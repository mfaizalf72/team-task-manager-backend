package com.faizal.teamtaskmanager.service;

import com.faizal.teamtaskmanager.dto.ProjectRequest;
import com.faizal.teamtaskmanager.entity.Project;
import com.faizal.teamtaskmanager.entity.User;
import com.faizal.teamtaskmanager.repository.ProjectRepository;
import com.faizal.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project createProject(ProjectRequest request, String email) {

        User creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!creator.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can create project");
        }

        Project project = Project.builder()
                .projectName(request.projectName())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdBy(creator)
                .build();

        project.setTeamMembers(new java.util.HashSet<>());
        project.getTeamMembers().add(creator);

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().equals("ADMIN")) {
            return projectRepository.findAll();
        }

        return projectRepository.findAll()
                .stream()
                .filter(project -> project.getTeamMembers().contains(user))
                .toList();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project updateProject(Long id, ProjectRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update project");
        }

        Project project = getProjectById(id);

        project.setProjectName(request.projectName());
        project.setDescription(request.description());
        project.setStartDate(request.startDate());
        project.setEndDate(request.endDate());

        return projectRepository.save(project);
    }

    public String deleteProject(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can delete project");
        }

        projectRepository.deleteById(id);

        return "Project deleted successfully";
    }

    public Project addMemberToProject(Long projectId, Long userId, String email) {

        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!admin.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can add members");
        }

        Project project = getProjectById(projectId);

        User member = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        project.getTeamMembers().add(member);

        return projectRepository.save(project);
    }
}