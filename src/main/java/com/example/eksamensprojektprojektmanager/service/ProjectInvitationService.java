package com.example.eksamensprojektprojektmanager.service;

import com.example.eksamensprojektprojektmanager.model.ProjectInvitation;
import com.example.eksamensprojektprojektmanager.repository.ProjectInvitationRepository;
import com.example.eksamensprojektprojektmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInvitationService {


    private ProjectInvitationRepository projectInvitationRepository;
    @Autowired
    public ProjectInvitationService(ProjectInvitationRepository projectInvitationRepository) {
        this.projectInvitationRepository = projectInvitationRepository;
    }




    public void inviteUserToProject(long projectId, long senderUserId, long receiverUserId) {
        projectInvitationRepository.inviteUserToProject(projectId, senderUserId, receiverUserId);
    }

    public void acceptInvite(Long inviteId){
        projectInvitationRepository.acceptInvite(inviteId);
    }

    public List<Long> getAcceptedProjectIdsByUserId(Long userId) {
        return projectInvitationRepository.getAcceptedProjectIdsByUserId(userId);
    }

    public List<ProjectInvitation> getInvitesByUserId(Long userId) {
        return projectInvitationRepository.getInvitesByUserId(userId);
    }


}
