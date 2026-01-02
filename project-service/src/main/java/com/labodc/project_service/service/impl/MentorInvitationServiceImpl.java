package com.labodc.project_service.service.impl;

import com.labodc.project_service.entity.MentorInvitation;
import com.labodc.project_service.entity.ProjectMentor;
import com.labodc.project_service.repository.MentorInvitationRepository;
import com.labodc.project_service.repository.ProjectMentorRepository;
import com.labodc.project_service.service.MentorInvitationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class MentorInvitationServiceImpl implements MentorInvitationService {
    private final MentorInvitationRepository invitationRepo;
    private final ProjectMentorRepository projectMentorRepo;

    @Override
    @Transactional
    public void acceptInvitation(String invitationId, String mentorId) {
        UUID invitationUUID = UUID.fromString(invitationId);

        MentorInvitation invitation = invitationRepo
                .findByIdAndMentorIdAndStatus(invitationId, mentorId, "PENDING")
                .orElseThrow(() -> new RuntimeException("Invitation not found or already processed"));

        invitation.setStatus("ACCEPTED");
        invitation.setRespondedAt(LocalDateTime.now());
        invitationRepo.save(invitation);
        ProjectMentor projectMentor = new ProjectMentor();
        projectMentor.setProjectId(invitation.getProjectId());
        projectMentor.setMentorId(invitation.getMentorId());
        projectMentor.setJoinedAt(LocalDateTime.now());
        projectMentor.setRole("MENTOR"); // Role mặc định khi tham gia
        projectMentor.setStatus("ACTIVE");

        projectMentorRepo.save(projectMentor);
    }
}
