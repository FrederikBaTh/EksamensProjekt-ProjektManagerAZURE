package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.repository.SubprojectRepository;
import com.example.eksamensprojektprojektmanager.repository.UserProjectRoleRepository;
import com.example.eksamensprojektprojektmanager.repository.UserSubprojectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {

    private final SubprojectRepository subprojectRepository;
    private final UserSubprojectAssignmentRepository userSubprojectAssignmentRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    @Autowired
    public SubprojectService(SubprojectRepository subprojectRepository, UserSubprojectAssignmentRepository userSubprojectAssignmentRepository, UserProjectRoleRepository userProjectRoleRepository) {
        this.subprojectRepository = subprojectRepository;
        this.userSubprojectAssignmentRepository = userSubprojectAssignmentRepository;
        this.userProjectRoleRepository = userProjectRoleRepository;
    }

    public void addSubproject(Subproject subproject, Long projectId) {
        subprojectRepository.addSubproject(subproject, projectId);
    }

    public List<Subproject> getSubprojectsByProjectId(Long projectId) {
        return subprojectRepository.findByProjectId(projectId);
    }

    public void deleteSubprojectById(Long subprojectId) {
        subprojectRepository.deleteSubprojectById(subprojectId);
    }

    public Subproject getSubprojectById(Long subprojectId) {
        return subprojectRepository.findById(subprojectId);
    }

    public Subproject updateSubproject(Subproject subproject) {
        return subprojectRepository.updateSubproject(subproject);
    }

    public List<Account> getAssignedUsers(Long subprojectId) {
        return userSubprojectAssignmentRepository.findAssignedUsersBySubprojectId(subprojectId);
    }

    public boolean userCanEditSubproject(Long userId, Long projectId) {
        String role = userProjectRoleRepository.getRoleByUserIdAndProjectId(userId, projectId);
        return role.equals("admin");
    }
}