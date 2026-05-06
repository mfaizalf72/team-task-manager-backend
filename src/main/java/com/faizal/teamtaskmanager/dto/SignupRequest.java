package com.faizal.teamtaskmanager.dto;

import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @Size(min = 6) String password,
        String role
) {}