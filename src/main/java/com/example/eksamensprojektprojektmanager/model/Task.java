package com.example.eksamensprojektprojektmanager.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime date;
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
    public Long getId() {
        return id;
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
    public void setId(Long id) {
        this.id = id;
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
}