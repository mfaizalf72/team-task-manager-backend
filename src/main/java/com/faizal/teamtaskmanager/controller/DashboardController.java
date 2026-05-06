package com.faizal.teamtaskmanager.controller;

import com.faizal.teamtaskmanager.dto.DashboardResponse;
import com.faizal.teamtaskmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboard(Authentication authentication) {
        String email = authentication.getName();
        return dashboardService.getDashboard(email);
    }
}