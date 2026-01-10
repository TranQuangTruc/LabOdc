package com.labodc.project_service.repository;

import com.labodc.project_service.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, String> {
}
