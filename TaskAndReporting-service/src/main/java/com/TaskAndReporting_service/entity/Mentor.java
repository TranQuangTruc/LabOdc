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
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    @Column(columnDefinition = "TEXT")
    String expertise;
    int yearOfExperience;
    String company;
    String CVUrl;
    @Column(columnDefinition = "TEXT")
    String bio;
}
