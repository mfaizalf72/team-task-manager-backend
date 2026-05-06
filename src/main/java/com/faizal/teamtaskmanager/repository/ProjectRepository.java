package com.faizal.teamtaskmanager.repository;

import com.faizal.teamtaskmanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}