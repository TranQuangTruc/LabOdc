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

    // 1. API Chấp nhận: Lấy mentorId từ JSON Body
    @PostMapping("/{id}/accept")
    public ResponseEntity<String> accept(
            @PathVariable String id,
            @RequestBody AcceptInvitationRequest request) {

        // Lấy mentorId từ JSON thay vì ghi cứng "MENTOR-001"
        invitationService.acceptInvitation(id, request.getMentorId());

        return ResponseEntity.ok("Successfully accepted the invitation and joined the project.");
    }

    // 2. API Tạo test: Lấy các thông tin từ JSON Body
    @PostMapping("/create-test")
    public ResponseEntity<MentorInvitation> createTest(@RequestBody AcceptInvitationRequest request) {
        MentorInvitation invitation = new MentorInvitation();

        // Lấy dữ liệu từ Postman gửi lên
        invitation.setProjectId(request.getProjectId());
        invitation.setMentorId(request.getMentorId());

        invitation.setStatus("PENDING");
        invitation.setInvitedAt(LocalDateTime.now());

        return ResponseEntity.ok(invitationRepository.save(invitation));
    }
}