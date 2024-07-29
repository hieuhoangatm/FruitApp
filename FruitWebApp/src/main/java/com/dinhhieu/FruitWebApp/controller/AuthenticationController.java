package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.AuthReq.AuthenticationRequest;
import com.dinhhieu.FruitWebApp.dto.request.VerifyTokenRequest;
import com.dinhhieu.FruitWebApp.dto.response.AuthRes.AuthenticationResponse;
import com.dinhhieu.FruitWebApp.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("verify-token")
    ResponData<?> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest) throws ParseException, JOSEException {
        return new ResponData<>(HttpStatus.OK.value(), "verify token status", this.authenticationService.verifyTokenResponse(verifyTokenRequest));
    }
}
