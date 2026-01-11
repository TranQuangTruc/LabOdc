package com.TaskAndReporting_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.DecimalFormat;
import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String enterpriseId;
    String title;
    @Column(columnDefinition = "TEXT")
    String description;
    float budget;
    String status;
    String approvedBy;
    Instant createAt;
    Instant updateAt;
    String updateBy;
}
