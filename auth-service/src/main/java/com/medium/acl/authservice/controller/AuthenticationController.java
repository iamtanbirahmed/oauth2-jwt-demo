package com.medium.acl.authservice.controller;

import com.medium.acl.authservice.model.AuthenticationRequest;
import com.medium.acl.authservice.service.JwtUtilService;
import com.medium.acl.authservice.service.ParticipantService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Jwt-Authentication")
@RestController
@RequestMapping("api/v1.0.1/jwt")
public class AuthenticationController {

    private final JwtUtilService jwtUtilService;
    private final ParticipantService participantService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUtilService jwtUtilService, ParticipantService participantService, AuthenticationManager authenticationManager) {
        this.jwtUtilService = jwtUtilService;
        this.participantService = participantService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        UserDetails participantDetails = participantService.loadUserByUsername(authenticationRequest.getUsername());

        String jwtToken = jwtUtilService.generateToken(participantDetails);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtToken
                )
                .body(participantDetails);
    }

}
