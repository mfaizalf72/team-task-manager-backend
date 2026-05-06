package com.faizal.teamtaskmanager.dto;

public record AuthResponse(
        String token,
        String role,
        String name
) {}