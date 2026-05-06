package com.faizal.teamtaskmanager.dto;

import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate,
        Long assignedToId,
        Long projectId
) {}