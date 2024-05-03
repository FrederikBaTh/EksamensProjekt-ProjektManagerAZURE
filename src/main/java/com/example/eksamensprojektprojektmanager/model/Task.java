package com.example.eksamensprojektprojektmanager.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime deadline;

    public Task(String name, String description, LocalDateTime date, LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.deadline = deadline;
    }

    public Task() {

    }

    //getters
    public Long getTask_id() {
        return task_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    //Setters
    public void setTask_id(Long id) {
        this.task_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public Task(String name, String description, LocalDateTime date, LocalDateTime deadline, Integer projectId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.deadline = deadline;
        this.projectId = projectId;
    }
}