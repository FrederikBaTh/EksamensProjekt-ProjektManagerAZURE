package com.example.eksamensprojektprojektmanager.model;


import jakarta.persistence.*;

@Entity
@Table(name = "subproject")
public class Subproject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name")
    private int subproject_id;

    @Column(name = "project_id",nullable = false)
    private int project_id;

    @Column(name = "name",nullable = false, unique = false)
    private String subprojectname;






}
