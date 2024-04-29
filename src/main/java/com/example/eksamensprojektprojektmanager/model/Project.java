package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;

public class Project {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false, unique = false)
    public String projectName;

    @Column(nullable = false)
    public Date startDate;

    @Column(nullable = true)
    public Date projectDeadline;

    @Column(nullable = true)
    public String projectStatus;


    public Project(String projectName, Date startDate, Date projectDeadline, String projectStatus) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.projectDeadline = projectDeadline;
        this.projectStatus = projectStatus;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(Date projectDeadline) {
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
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", projectDeadline=" + projectDeadline +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
