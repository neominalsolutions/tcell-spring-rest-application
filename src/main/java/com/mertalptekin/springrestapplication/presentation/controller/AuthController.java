package com.mertalptekin.springrestapplication.presentation.controller;

import com.mertalptekin.springrestapplication.application.token.TokenRequest;
import com.mertalptekin.springrestapplication.application.token.TokenResponse;
import com.mertalptekin.springrestapplication.infra.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }



    @PostMapping("/connect/trail-token")
    public ResponseEntity<TokenResponse> token(@RequestBody TokenRequest tokenRequest) {

        UserDetails userDetails = User.withUsername(tokenRequest.username())
                .password(tokenRequest.password())
                .authorities("USER")
                .build();

        String accessToken = jwtService.generateToken(userDetails);


        return ResponseEntity.ok(new TokenResponse(accessToken));
    }

}
