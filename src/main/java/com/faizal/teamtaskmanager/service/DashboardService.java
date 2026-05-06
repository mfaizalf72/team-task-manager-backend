package com.faizal.teamtaskmanager.service;

import com.faizal.teamtaskmanager.dto.DashboardResponse;
import com.faizal.teamtaskmanager.entity.Task;
import com.faizal.teamtaskmanager.entity.User;
import com.faizal.teamtaskmanager.repository.ProjectRepository;
import com.faizal.teamtaskmanager.repository.TaskRepository;
import com.faizal.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks;

        long totalProjects;

        if (user.getRole().equals("ADMIN")) {
            tasks = taskRepository.findAll();
            totalProjects = projectRepository.count();
        } else {
            tasks = taskRepository.findByAssignedTo(user);
            totalProjects = 0;
        }

        long totalTasks = tasks.size();

        long completedTasks = tasks.stream()
                .filter(task -> "COMPLETED".equals(task.getStatus()))
                .count();

        long pendingTasks = tasks.stream()
                .filter(task -> "TODO".equals(task.getStatus()))
                .count();

        long inProgressTasks = tasks.stream()
                .filter(task -> "IN_PROGRESS".equals(task.getStatus()))
                .count();

        long overdueTasks = tasks.stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now()))
                .filter(task -> !"COMPLETED".equals(task.getStatus()))
                .count();

        long myAssignedTasks = taskRepository.findByAssignedTo(user).size();

        return new DashboardResponse(
                totalProjects,
                totalTasks,
                completedTasks,
                pendingTasks,
                inProgressTasks,
                overdueTasks,
                myAssignedTasks
        );
    }
}