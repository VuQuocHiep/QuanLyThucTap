package com.Hiep.B23DCCN295.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.AuthenticationRequest;
import com.Hiep.B23DCCN295.dto.request.IntrospectRequest;
import com.Hiep.B23DCCN295.dto.response.AuthenticationResponse;
import com.Hiep.B23DCCN295.dto.response.IntrospectResponse;
import com.Hiep.B23DCCN295.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
        return ResponseEntity.ok(authenticationService.introspect(request));
    }
}
