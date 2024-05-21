package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Long experienceId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "skill", nullable = false)
    private String skill;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    public Experience() {
    }

    public Experience(Long userId, String skill, int yearsOfExperience) {
        this.userId = userId;
        this.skill = skill;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
