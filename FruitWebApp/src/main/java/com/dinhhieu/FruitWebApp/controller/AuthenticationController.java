package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.AuthReq.AuthenticationRequest;
import com.dinhhieu.FruitWebApp.dto.request.LogoutRequest;
import com.dinhhieu.FruitWebApp.dto.request.RefreshTokenRequest;
import com.dinhhieu.FruitWebApp.dto.request.VerifyTokenRequest;
import com.dinhhieu.FruitWebApp.dto.response.AuthRes.AuthenticationResponse;
import com.dinhhieu.FruitWebApp.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("log-in")
    ResponData<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
//        boolean result_login = this.authenticationService.authenticate(authenticationRequest);
        return new ResponData<>(HttpStatus.OK.value(), "login status", this.authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/verify-token")
    ResponData<?> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest) throws ParseException, JOSEException {
        return new ResponData<>(HttpStatus.OK.value(), "verify token status", this.authenticationService.verifyTokenResponse(verifyTokenRequest));
    }

    @PostMapping("/logout")
    ResponData<?> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        this.authenticationService.logout(logoutRequest);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "logout account");
    }


    @PostMapping("/refresh-token")
    ResponData<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        return new ResponData<>(HttpStatus.OK.value(), "refresh token", this.authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgot-password")
    public ResponData<?> forgotPassword(@RequestParam String email){
        return new ResponData<>(HttpStatus.OK.value(),"forgot password",authenticationService.forgotPassword(email));
    }

    @PostMapping("/set-password")
    public ResponData<?> setPassword(@RequestParam String email, @RequestHeader String password){
        return new ResponData<>(HttpStatus.OK.value(), "reset password", authenticationService.setPassword(email, password));
    }


}
