package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.repository.UserSubprojectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubprojectAssignmentService {

    private final UserSubprojectAssignmentRepository userSubprojectAssignmentRepository;
    private final SubprojectService subprojectService;

    @Autowired
    public UserSubprojectAssignmentService(UserSubprojectAssignmentRepository userSubprojectAssignmentRepository, SubprojectService subprojectService) {
        this.userSubprojectAssignmentRepository = userSubprojectAssignmentRepository;
        this.subprojectService = subprojectService;
    }

    public void assignUserToSubproject(Long userId, Long subprojectId) {
        userSubprojectAssignmentRepository.assignUserToSubproject(userId, subprojectId);

        // Fetch the updated list of assigned users for the subproject
        List<Account> assignedUsers = subprojectService.getAssignedUsers(subprojectId);

        // Find the subproject and update its assignedUsers list
        Subproject subproject = subprojectService.getSubprojectById(subprojectId);
        if (subproject != null) {
            subproject.setAssignedUsers(assignedUsers);
        }
    }
}