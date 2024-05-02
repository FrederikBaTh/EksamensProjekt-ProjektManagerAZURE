package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

public class Project {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    @Column(nullable = false, unique = false)
    public String projectName;

    @Column(nullable = false)
    public LocalDate startDate;

    @Column(nullable = true)
    public LocalDate projectDeadline;

    @Column(nullable = true)
    public String projectStatus;

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    public Project(String projectName, LocalDate startDate, LocalDate projectDeadline ) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.projectDeadline = projectDeadline;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + project_id +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", projectDeadline=" + projectDeadline +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
