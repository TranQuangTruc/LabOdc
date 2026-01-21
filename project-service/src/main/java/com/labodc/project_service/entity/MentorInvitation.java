package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "mentor_invitation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentorInvitation {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String id = java.util.UUID.randomUUID().toString();

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "mentor_id", nullable = false)
    private String mentorId;

    @Column(name = "status")
    private String status;

    @Column(name = "invited_at")
    private LocalDateTime invitedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}