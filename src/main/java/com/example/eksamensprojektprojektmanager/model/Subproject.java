package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subproject")
public class Subproject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subproject_id")
    private Long subproject_id;

    @Column(name = "project_id", nullable = false)
    private Long project_id;

    @Column(name = "name", nullable = false, unique = false)
    private String subprojectname;

    @Column(name = "description", nullable = false, unique = false)
    private String description;

    @Column(name = "startDate", nullable = false, unique = false)
    private LocalDate startDate;

    @Column(name = "deadline", nullable = false, unique = false)
    private LocalDate deadline;



    public Subproject() {
    }

    public Subproject(String subprojectname, String description, LocalDate startDate, LocalDate deadline) {
        this.subprojectname = subprojectname;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
    }


    // Getters
    public Long getSubproject_id() {
        return subproject_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public String getSubprojectname() {
        return subprojectname;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    // Setters
    public void setSubproject_id(Long subproject_id) {
        this.subproject_id = subproject_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public void setSubprojectname(String subprojectname) {
        this.subprojectname = subprojectname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


    @Override
    public String toString() {
        return "Subproject{" +
                "subproject_id=" + subproject_id +
                ", project_id=" + project_id +
                ", subprojectname='" + subprojectname + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                '}';
    }
}