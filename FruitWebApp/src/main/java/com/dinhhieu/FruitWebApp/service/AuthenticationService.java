package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.AuthReq.AuthenticationRequest;
import com.dinhhieu.FruitWebApp.dto.request.LogoutRequest;
import com.dinhhieu.FruitWebApp.dto.request.RefreshTokenRequest;
import com.dinhhieu.FruitWebApp.dto.request.VerifyTokenRequest;
import com.dinhhieu.FruitWebApp.dto.response.AuthRes.AuthenticationResponse;
import com.dinhhieu.FruitWebApp.dto.response.VerifyTokenResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.InvalidatedToken;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import com.dinhhieu.FruitWebApp.repository.InvalidatedTokenRepository;
import com.dinhhieu.FruitWebApp.util.EmailUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final CustomerRepository customerRepository;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    private final EmailUtil emailUtil;


    @NonFinal
    protected final String SIGNER_KEY = "9CD+6WbRMMdb0l2BHVdztaEVeAoAX89m11Ez26LH4sQIkQ/X2nPVF9KTReRT4Z2n";


    public VerifyTokenResponse verifyTokenResponse(VerifyTokenRequest verifyTokenRequest) throws JOSEException, ParseException {
        String token = verifyTokenRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verifed = signedJWT.verify(verifier); // kiem tra xem token dung hay khong

        // check token-logout exist in table invalidated_token in  database
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException("account not be unauthenticated");
        }


        return VerifyTokenResponse.builder().valid(verifed && expityTime.after(new Date())).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        var customer = customerRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(()->new RuntimeException("account not exist"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        boolean authenticate =  passwordEncoder.matches(authenticationRequest.getPassword(), customer.getPassword());
        if(!authenticate){
            throw new RuntimeException("Login failed");
        }

        String token = genarateToken(customer);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String genarateToken(Customer customer){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(customer.getEmail())
                .issuer("dinhhieu")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("JWT keyt", "nội dung clam của JWT")
                .claim("scope",buildScope(customer))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Customer customer){
//        StringJoiner stringJoiner = new StringJoiner(" ");
//        if(!CollectionUtils.isEmpty(customer.getRole())){
//            customer.getRole().forEach(stringJoiner::add);
//        }
//        return stringJoiner.toString();
        StringBuilder stringBuilder = new StringBuilder();
//        if(!customer.getRole().isEmpty()){
//            for(String role : customer.getRole()){
//                stringBuilder.append(role+" ");
//            }
//        }
        log.info("customer role : "+ customer.getRoles().toString());
        if(!customer.getRoles().isEmpty()){
            customer.getRoles().forEach(
                    role -> {
                        stringBuilder.append(role.getName()+" ");
                        if(!role.getPermissions().isEmpty()){
                            role.getPermissions().forEach(
                                    permission -> stringBuilder.append(permission.getName()+" ")
                            );
                        }
                    }

            );

        }
        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        SignedJWT signToken = verifyTokenLogout(logoutRequest.getToken());

        String jitID = signToken.getJWTClaimsSet().getJWTID();

        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jitID)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyTokenLogout(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if(!(verified && expiryTime.after(new Date()))){
            throw new RuntimeException("account not be authenticated");
        }

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyTokenLogout(refreshTokenRequest.getToken());

        String jitID = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jitID)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        String emailSubject = signedJWT.getJWTClaimsSet().getSubject();

        Customer customer = customerRepository.findByEmail(emailSubject).orElseThrow(() -> new RuntimeException("Not found customer with email "+emailSubject));

        String token = genarateToken(customer);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public String forgotPassword(String email) {
//        SecurityContext context = SecurityContextHolder.getContext();
//        String emailLogin = context.getAuthentication().getName();
//
//        if (!email.equalsIgnoreCase(emailLogin)) {
//            throw new IllegalArgumentException("Email does not match with the logged in account");
//        }
//
//        Customer customer = customerRepository.findByEmail(emailLogin)
//                .orElseThrow(() -> new IllegalArgumentException("Email does not exist"));

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email does not exist"));

        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set password email", e);
        }
        return "Please check your email to set a new password.";
    }

    public String setPassword(String email, String password){
//        SecurityContext context = SecurityContextHolder.getContext();
//        String emailLogin = context.getAuthentication().getName();
//
//        if (!email.equalsIgnoreCase(emailLogin)) {
//            throw new IllegalArgumentException("Email does not match with the logged in account");
//        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email does not exist"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        customer.setPassword(passwordEncoder.encode(password));
        customerRepository.save(customer);
        return "set new password successfully";
    }
}
