package com.labodc.project_service.repository;

import com.labodc.project_service.entity.MentorInvitation;
import com.labodc.project_service.entity.ProjectMentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MentorInvitationRepository extends JpaRepository<MentorInvitation, UUID> {
    Optional<MentorInvitation> findByIdAndMentorIdAndStatus(String id, String mentorId, String status);
}
