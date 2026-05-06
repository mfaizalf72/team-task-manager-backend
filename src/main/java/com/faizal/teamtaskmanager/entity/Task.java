package com.faizal.teamtaskmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String status; // TODO, IN_PROGRESS, COMPLETED

    private String priority; // LOW, MEDIUM, HIGH

    private LocalDate dueDate;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private Project project;
}