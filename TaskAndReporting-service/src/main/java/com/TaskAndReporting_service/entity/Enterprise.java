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
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    String name;
    String phoneNumber;
    String address;
    String website;
    @Column(columnDefinition = "TEXT")
    String description;
    String status;
}
