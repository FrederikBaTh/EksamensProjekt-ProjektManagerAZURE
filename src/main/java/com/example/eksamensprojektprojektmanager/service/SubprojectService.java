package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.model.Subproject;
import com.example.eksamensprojektprojektmanager.repository.SubprojectRepository;
import com.example.eksamensprojektprojektmanager.repository.UserSubprojectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {

    private final SubprojectRepository subprojectRepository;
    private final UserSubprojectAssignmentRepository userSubprojectAssignmentRepository;

    @Autowired
    public SubprojectService(SubprojectRepository subprojectRepository, UserSubprojectAssignmentRepository userSubprojectAssignmentRepository) {
        this.subprojectRepository = subprojectRepository;
        this.userSubprojectAssignmentRepository = userSubprojectAssignmentRepository;
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



}