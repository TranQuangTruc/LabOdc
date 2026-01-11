package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplication extends BaseEntity {
    private String projectId;
    private String talentId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String status;
}