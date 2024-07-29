package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.AuthReq.AuthenticationRequest;
import com.dinhhieu.FruitWebApp.dto.request.VerifyTokenRequest;
import com.dinhhieu.FruitWebApp.dto.response.AuthRes.AuthenticationResponse;
import com.dinhhieu.FruitWebApp.dto.response.VerifyTokenResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;

    @NonFinal
    protected final String SIGNER_KEY = "9CD+6WbRMMdb0l2BHVdztaEVeAoAX89m11Ez26LH4sQIkQ/X2nPVF9KTReRT4Z2n";


    public VerifyTokenResponse verifyTokenResponse(VerifyTokenRequest verifyTokenRequest) throws JOSEException, ParseException {
        String token = verifyTokenRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verifed = signedJWT.verify(verifier); // kiem tra xem token dung hay khong

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
        if(!customer.getRole().isEmpty()){
            for(String role : customer.getRole()){
                stringBuilder.append(role+" ");
            }
        }
        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
