package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, String> {
}
