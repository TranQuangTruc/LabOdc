package com.labodc.project_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @Column(length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String id = UUID.randomUUID().toString();

    private LocalDateTime createdDate = LocalDateTime.now();
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private boolean isDeleted = false;

    @Column(columnDefinition = "TEXT")
    private String note;
}