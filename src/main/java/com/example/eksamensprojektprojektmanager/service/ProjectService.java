package com.example.eksamensprojektprojektmanager.service;


import com.example.eksamensprojektprojektmanager.model.Project;
import com.example.eksamensprojektprojektmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }



    public Project addProject(Project project, String userIdString) {
        if (userIdString != null && !userIdString.isEmpty()) {
            int user_id = Integer.parseInt(userIdString);
            project.setUserId(user_id);

            if (project.getProjectName() == null || project.getProjectName().isEmpty() || project.getProjectName().length() > 100) {
                throw new IllegalArgumentException("Project name must be between 1 and 100 characters.");
            }

            if (projectRepository.existsByNameAndUserId(project.getProjectName(), user_id)) {
                throw new IllegalStateException("Project with the same name already exists for this user.");
            }

            return projectRepository.addProject(project);
        } else {
            throw new IllegalArgumentException("User ID is null or empty.");
        }
    }

    public List<Project> getProjectsByUserId(int userId) {
        List<Project> projects = projectRepository.getProjectsByUserId(userId);
        return projects;
    }



}
