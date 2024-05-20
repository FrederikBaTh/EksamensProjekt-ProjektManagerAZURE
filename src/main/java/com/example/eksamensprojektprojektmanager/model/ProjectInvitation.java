package com.example.eksamensprojektprojektmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_invitations")
public class ProjectInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long invitationId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "sender_user_id")
    private Long senderUserId;

    @Column(name = "receiver_user_id")
    private Long receiverUserId;

    @Column(name = "projectname")
    private String projectName;

    @Column(name = "senderUsername")
    private String senderUserName;

    @Column(name = "recieverUsername")
    private String receiverUserName;



    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvitationStatus status;

    public ProjectInvitation(){

    }

    public ProjectInvitation(Long invitationId, Long projectId, Long senderUserId, Long receiverUserId, InvitationStatus status) {
        this.invitationId = invitationId;
        this.projectId = projectId;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.status = status;
    }



    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getReceiverUserId() {
        return receiverUserId;
    }

    public String getProjectName() {
        return projectName;
    }
    public String getSenderUserName() {
        return senderUserName;
    }
    public String getReceiverUserName() {
        return receiverUserName;
    }
    public void setReceiverUserId(Long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }
    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }
}