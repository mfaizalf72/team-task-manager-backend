package com.faizal.teamtaskmanager.controller;

import com.faizal.teamtaskmanager.dto.AuthResponse;
import com.faizal.teamtaskmanager.dto.LoginRequest;
import com.faizal.teamtaskmanager.dto.SignupRequest;
import com.faizal.teamtaskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}