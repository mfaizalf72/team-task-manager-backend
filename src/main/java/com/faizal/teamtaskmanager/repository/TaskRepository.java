package com.faizal.teamtaskmanager.repository;

import com.faizal.teamtaskmanager.entity.Task;
import com.faizal.teamtaskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignedTo(User user);
}