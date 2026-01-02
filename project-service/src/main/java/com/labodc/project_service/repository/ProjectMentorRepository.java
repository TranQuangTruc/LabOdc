package com.labodc.project_service.repository;

import com.labodc.project_service.entity.ProjectMentor; // Import đúng Entity ProjectMentor
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectMentorRepository extends JpaRepository<ProjectMentor, UUID> {

}