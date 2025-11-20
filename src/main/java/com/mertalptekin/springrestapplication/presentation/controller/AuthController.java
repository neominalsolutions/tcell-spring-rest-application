package com.mertalptekin.springrestapplication.presentation.controller;

import com.mertalptekin.springrestapplication.application.token.TokenRequest;
import com.mertalptekin.springrestapplication.application.token.TokenResponse;
import com.mertalptekin.springrestapplication.domain.entity.AppUser;
import com.mertalptekin.springrestapplication.infra.jwt.JwtService;
import com.mertalptekin.springrestapplication.infra.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/connect/trail-token")
    public ResponseEntity<TokenResponse> token(@RequestBody TokenRequest tokenRequest) {

        UserDetails userDetails = User.withUsername(tokenRequest.username())
                .password(tokenRequest.password())
                .passwordEncoder(passwordEncoder::encode)
                .authorities("USER")
                .build();

        String accessToken = jwtService.generateToken(userDetails);


        return ResponseEntity.ok(new TokenResponse(accessToken));
    }



    @PostMapping("/connect/token")
    public ResponseEntity<TokenResponse> connectToken(@RequestBody TokenRequest tokenRequest) {


        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.username(), tokenRequest.password()));

        if(!auth.isAuthenticated()){
            return ResponseEntity.status(401).build();
        }

        SecurityContextHolder.getContext().setAuthentication(auth);


        String accessToken = jwtService.generateToken((UserDetails) auth.getPrincipal());


        return ResponseEntity.ok(new TokenResponse(accessToken));
    }


    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody TokenRequest tokenRequest) {
        UserDetails userDetails = User.withUsername(tokenRequest.username())
                .password(tokenRequest.password())
                .passwordEncoder(passwordEncoder::encode)
                .authorities("USER")
                .build();

        AppUser user =  this.modelMapper.map(userDetails,  AppUser.class);

        userRepository.save(user);

        return  ResponseEntity.ok(new TokenResponse(jwtService.generateToken(userDetails)));

    }



}
