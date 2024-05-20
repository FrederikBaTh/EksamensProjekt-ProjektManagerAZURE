package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.InvitationStatus;
import com.example.eksamensprojektprojektmanager.model.ProjectInvitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectInvitationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public void inviteUserToProject(long projectId, long senderUserId, long receiverUserId) {
        // Create a new project invitation
        ProjectInvitation invitation = new ProjectInvitation();
        invitation.setProjectId(projectId);
        invitation.setSenderUserId(senderUserId);
        invitation.setReceiverUserId(receiverUserId);
        invitation.setStatus(InvitationStatus.PENDING); // Assuming InvitationStatus is an enum

        // Save the invitation to the database
        String insertInvitationQuery = "INSERT INTO project_invitations (project_id, sender_user_id, receiver_user_id, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertInvitationQuery, invitation.getProjectId(), invitation.getSenderUserId(), invitation.getReceiverUserId(), invitation.getStatus().toString());

        // Assign the viewer role to the invited user in the user_project_roles table
        String insertRoleQuery = "INSERT INTO user_project_roles (user_id, project_id, role) VALUES (?, ?, 'viewer')";
        jdbcTemplate.update(insertRoleQuery, receiverUserId, projectId);
    }



    public void acceptInvite(Long inviteId) {
        String sql = "UPDATE project_invitations SET status = ? WHERE invitation_id = ?";
        jdbcTemplate.update(sql, "ACCEPTED", inviteId);
    }


    public List<Long> getAcceptedProjectIdsByUserId(Long userId) {
        String sql = "SELECT project_id FROM project_invitations WHERE receiver_user_id = ? AND status = 'ACCEPTED'";
        return jdbcTemplate.queryForList(sql, new Object[]{userId}, Long.class);
    }
    public List<ProjectInvitation> getInvitesByUserId(Long userId) {
        String sql = "SELECT * FROM project_invitations WHERE receiver_user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, rowNum) -> {
            ProjectInvitation invitation = new ProjectInvitation();
            invitation.setInvitationId(resultSet.getLong("invitation_id"));
            invitation.setProjectId(resultSet.getLong("project_id"));
            invitation.setSenderUserId(resultSet.getLong("sender_user_id"));
            invitation.setReceiverUserId(resultSet.getLong("receiver_user_id"));
            invitation.setStatus(InvitationStatus.valueOf(resultSet.getString("status").toUpperCase()));
            return invitation;
        });
    }
    public List<Long> getAcceptedUserIdsByProjectId(Long projectId) {
        String sql = "SELECT receiver_user_id FROM project_invitations WHERE project_id = ? AND status = 'ACCEPTED'";
        return jdbcTemplate.queryForList(sql, new Object[]{projectId}, Long.class);
    }


    public void deleteInvitationsForUser(Long userId) {
        String sql = "DELETE FROM project_invitations WHERE receiver_user_id = ?";
        jdbcTemplate.update(sql, userId);
    }



}