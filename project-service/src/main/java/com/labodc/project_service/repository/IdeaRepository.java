package com.labodc.project_service.repository;

import com.labodc.project_service.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, String> {
    List<Idea> findByStatus(String status);
}
