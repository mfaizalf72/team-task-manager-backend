package com.faizal.teamtaskmanager.controller;

import com.faizal.teamtaskmanager.dto.TaskRequest;
import com.faizal.teamtaskmanager.entity.Task;
import com.faizal.teamtaskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.faizal.teamtaskmanager.dto.TaskResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request,
                           Authentication authentication) {
        String email = authentication.getName();
        return taskService.createTask(request, email);
    }

    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        String email = authentication.getName();
        return taskService.getAllTasks(email);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable Long projectId) {
        return taskService.getTasksByProject(projectId);
    }

    @GetMapping("/my")
    public List<Task> getMyTasks(Authentication authentication) {
        String email = authentication.getName();
        return taskService.getMyTasks(email);
    }

    @PatchMapping("/{taskId}/status")
    public Task updateTaskStatus(@PathVariable Long taskId,
                                 @RequestParam String status,
                                 Authentication authentication) {
        String email = authentication.getName();
        return taskService.updateTaskStatus(taskId, status, email);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId,
                           @RequestBody TaskRequest request,
                           Authentication authentication) {
        String email = authentication.getName();
        return taskService.updateTask(taskId, request, email);
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long taskId,
                             Authentication authentication) {
        String email = authentication.getName();
        return taskService.deleteTask(taskId, email);
    }
}