package com.faizal.teamtaskmanager.service;

import com.faizal.teamtaskmanager.dto.TaskRequest;
import com.faizal.teamtaskmanager.entity.Project;
import com.faizal.teamtaskmanager.entity.Task;
import com.faizal.teamtaskmanager.entity.User;
import com.faizal.teamtaskmanager.repository.ProjectRepository;
import com.faizal.teamtaskmanager.repository.TaskRepository;
import com.faizal.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.faizal.teamtaskmanager.dto.TaskResponse;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request, String email) {

        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!admin.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can create tasks");
        }

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User assignedUser = userRepository.findById(request.assignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status() == null ? "TODO" : request.status())
                .priority(request.priority() == null ? "MEDIUM" : request.priority())
                .dueDate(request.dueDate())
                .assignedTo(assignedUser)
                .project(project)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public List<Task> getAllTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().equals("ADMIN")) {
            return taskRepository.findAll();
        }

        return taskRepository.findByAssignedTo(user);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getMyTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByAssignedTo(user);
    }

    public Task updateTaskStatus(Long taskId, String status, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!user.getRole().equals("ADMIN") && !task.getAssignedTo().getId().equals(user.getId())) {
            throw new RuntimeException("You can update only your assigned task status");
        }

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, TaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update task details");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User assignedUser = userRepository.findById(request.assignedToId())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());
        task.setProject(project);
        task.setAssignedTo(assignedUser);

        return taskRepository.save(task);
    }

    public String deleteTask(Long taskId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can delete tasks");
        }

        taskRepository.deleteById(taskId);

        return "Task deleted successfully";
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getAssignedTo().getName(),
                task.getProject().getProjectName()
        );
    }
}