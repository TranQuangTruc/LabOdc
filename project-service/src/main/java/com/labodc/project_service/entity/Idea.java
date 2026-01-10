package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ideas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Idea extends BaseEntity {
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String ownerId;

    private String status;

    private String specialtyId;
}