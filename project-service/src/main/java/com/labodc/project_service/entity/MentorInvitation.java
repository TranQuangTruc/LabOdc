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
    @JdbcTypeCode(SqlTypes.VARCHAR) // Ép Hibernate lưu UUID dưới dạng chuỗi VARCHAR thay vì Binary
    private String id = java.util.UUID.randomUUID().toString(); // Tự động tạo ID chuỗi khi khởi tạo

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "mentor_id", nullable = false)
    private String mentorId;

    @Column(name = "status")
    private String status; // Giá trị sẽ là "PENDING", "ACCEPTED"

    @Column(name = "invited_at")
    private LocalDateTime invitedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}