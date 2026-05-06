package com.faizal.teamtaskmanager.controller;

import com.faizal.teamtaskmanager.dto.ProjectRequest;
import com.faizal.teamtaskmanager.entity.Project;
import com.faizal.teamtaskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project createProject(@RequestBody ProjectRequest request,
                                 Authentication authentication) {
        String email = authentication.getName();
        return projectService.createProject(request, email);
    }

    @GetMapping
    public List<Project> getAllProjects(Authentication authentication) {
        String email = authentication.getName();
        return projectService.getAllProjects(email);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @RequestBody ProjectRequest request,
                                 Authentication authentication) {
        String email = authentication.getName();
        return projectService.updateProject(id, request, email);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id,
                                Authentication authentication) {
        String email = authentication.getName();
        return projectService.deleteProject(id, email);
    }

    @PostMapping("/{projectId}/members/{userId}")
    public Project addMemberToProject(@PathVariable Long projectId,
                                      @PathVariable Long userId,
                                      Authentication authentication) {
        String email = authentication.getName();
        return projectService.addMemberToProject(projectId, userId, email);
    }
}