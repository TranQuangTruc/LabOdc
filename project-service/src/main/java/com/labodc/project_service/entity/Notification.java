package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification extends BaseEntity {
    private String userId;
    private String projectId;
    private String description;
    private boolean isRead = false;
}