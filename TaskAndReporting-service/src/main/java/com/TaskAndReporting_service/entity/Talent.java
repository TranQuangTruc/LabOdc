package com.TaskAndReporting_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    String fullname;
    @Column(columnDefinition = "TEXT")
    String skills;
    @Column(columnDefinition = "TEXT")
    String summary;
    String LinkedInUrl;
    String GithubUrl;
    String status;
}
