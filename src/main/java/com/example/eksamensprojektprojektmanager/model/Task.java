package com.example.eksamensprojektprojektmanager.model;


import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(name = "project_id",nullable = false)
    private Long projectId;

    @Column(name = "subproject_id",nullable = false)
    private Long subprojectId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    @ColumnDefault("'pending'")
    private String status;


    public Task(String name, String description, LocalDateTime date, LocalDateTime deadline, String status) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.deadline = deadline;
    }

    public Task() {
    }

    public Task(Long taskId, String name, String description, LocalDateTime date, LocalDateTime deadline, String status) {
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
    public Long getProjectId() {
        return projectId;
    }

    public Long getSubprojectId() {
        return subprojectId;
    }
    public String getStatus() {
        return status;
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

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public void setSubprojectId(Long subprojectId) {
        this.subprojectId = subprojectId;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    public Task(String name, String description, LocalDateTime date, LocalDateTime deadline, Long projectId, Long subprojectId, String status) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.deadline = deadline;
        this.projectId = projectId;
        this.subprojectId = subprojectId;
        this.status = status;
    }
}