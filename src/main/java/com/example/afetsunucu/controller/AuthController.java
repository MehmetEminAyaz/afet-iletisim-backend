package com.example.afetsunucu.controller;

import com.example.afetsunucu.dto.JwtResponse;
import com.example.afetsunucu.dto.LoginRequest;
import com.example.afetsunucu.dto.RegisterRequest;
import com.example.afetsunucu.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
