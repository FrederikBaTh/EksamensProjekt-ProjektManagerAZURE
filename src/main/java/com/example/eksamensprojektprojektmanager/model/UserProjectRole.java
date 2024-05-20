package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_project_roles")
public class UserProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_project_role_id")
    private Long userProjectRoleId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Long getUserProjectRoleId() {
        return userProjectRoleId;
    }

    public void setUserProjectRoleId(Long userProjectRoleId) {
        this.userProjectRoleId = userProjectRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}