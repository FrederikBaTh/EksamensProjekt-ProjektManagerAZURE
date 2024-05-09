package com.example.eksamensprojektprojektmanager.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;


    @Column(name = "is_admin",nullable = false)
    private boolean isAdmin;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "description")
    private String description;




    public Account() {
    }

    public Account(String username, String password, boolean isAdmin, String name, String company, String jobTitle, String description) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.description = description;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long userid) {
        this.user_id = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
