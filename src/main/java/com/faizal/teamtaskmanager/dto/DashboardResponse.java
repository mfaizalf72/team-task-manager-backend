package com.faizal.teamtaskmanager.dto;

public record DashboardResponse(
        long totalProjects,
        long totalTasks,
        long completedTasks,
        long pendingTasks,
        long inProgressTasks,
        long overdueTasks,
        long myAssignedTasks
) {}