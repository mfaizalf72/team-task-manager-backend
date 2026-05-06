package com.faizal.teamtaskmanager.dto;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate,
        String assignedUserName,
        String projectName
) {}