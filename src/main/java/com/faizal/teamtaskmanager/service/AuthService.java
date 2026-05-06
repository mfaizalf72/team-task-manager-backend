package com.faizal.teamtaskmanager.service;

import com.faizal.teamtaskmanager.dto.AuthResponse;
import com.faizal.teamtaskmanager.dto.LoginRequest;
import com.faizal.teamtaskmanager.dto.SignupRequest;
import com.faizal.teamtaskmanager.entity.User;
import com.faizal.teamtaskmanager.repository.UserRepository;
import com.faizal.teamtaskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role() == null ? "MEMBER" : request.role())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getRole(), user.getName());
    }
}