package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentRepository extends JpaRepository<Talent, String> {
}
