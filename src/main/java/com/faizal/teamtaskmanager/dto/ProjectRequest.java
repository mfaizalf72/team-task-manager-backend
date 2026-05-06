package com.faizal.teamtaskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ProjectRequest(
        @NotBlank String projectName,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {}