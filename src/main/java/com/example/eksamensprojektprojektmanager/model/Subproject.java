package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subprojects")
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
    @ColumnDefault("'1970-01-01'")
    private LocalDate startDate;

    @Column(name = "deadline", nullable = false, unique = false)
    private LocalDate deadline;

    @ManyToMany
    @JoinTable(
            name = "user_subproject_assignments",
            joinColumns = @JoinColumn(name = "subproject_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Account> assignedUsers;

    public void assignAccount(Account account) {
        if (assignedUsers == null) {
            assignedUsers = new ArrayList<>();
        }
        assignedUsers.add(account);
        account.getAssignedSubprojects().add(this);
    }

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

    public List<Account> getAssignedUsers() {
        return assignedUsers;
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

    public void setAssignedUsers(List<Account> assignedUsers) {
        this.assignedUsers = assignedUsers;
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