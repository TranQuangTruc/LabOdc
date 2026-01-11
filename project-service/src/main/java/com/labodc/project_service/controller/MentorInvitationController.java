package com.labodc.project_service.controller;

import com.labodc.project_service.dto.AcceptInvitationRequest; // Import DTO vừa tạo
import com.labodc.project_service.entity.MentorInvitation;
import com.labodc.project_service.repository.MentorInvitationRepository;
import com.labodc.project_service.service.MentorInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/projects/invitations")
@RequiredArgsConstructor
public class MentorInvitationController {

    private final MentorInvitationService invitationService;
    private final MentorInvitationRepository invitationRepository;


    @PostMapping("/{id}/accept")
    public ResponseEntity<String> accept(
            @PathVariable String id,
            @RequestBody AcceptInvitationRequest request) {


        invitationService.acceptInvitation(id, request.getMentorId());

        return ResponseEntity.ok("Successfully accepted the invitation and joined the project.");
    }

    @PostMapping("/create-test")
    public ResponseEntity<MentorInvitation> createTest(@RequestBody AcceptInvitationRequest request) {
        MentorInvitation invitation = new MentorInvitation();

        invitation.setProjectId(request.getProjectId());
        invitation.setMentorId(request.getMentorId());

        invitation.setStatus("PENDING");
        invitation.setInvitedAt(LocalDateTime.now());

        return ResponseEntity.ok(invitationRepository.save(invitation));
    }
}