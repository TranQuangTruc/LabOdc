package com.TaskAndReporting_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String projectId;
    String mentorId;
    String reportType;
    String reportFileUrl;
    @Column(columnDefinition = "TEXT")
    String summary;
    String status;
    Instant submittedAt;
}
