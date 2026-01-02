package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_mentor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMentor {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR) // Đảm bảo lưu dưới dạng String trong MySQL
    private String id = java.util.UUID.randomUUID().toString();

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "mentor_id", nullable = false)
    private String mentorId;

    @Column(name = "role")
    private String role; // Thường là "MENTOR"

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "status")
    private String status; // Thường là "ACTIVE"
}