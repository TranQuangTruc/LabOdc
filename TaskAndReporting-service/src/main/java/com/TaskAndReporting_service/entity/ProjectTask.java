package com.TaskAndReporting_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tools.jackson.databind.DatabindException;

import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @ElementCollection
    @CollectionTable(
            name = "project_task_talent",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "talent_id")
    List<String> talentIds;
    String talentId;
    String mentorId;
    String taskCode;
    String taskName;
    @Column(columnDefinition = "TEXT")
    String description;
    String priority;
    String status;
    Instant startDate;
    Instant dueDate;
    Instant completionDate;
    Instant createAt;
    Instant UpdateAt;
}
