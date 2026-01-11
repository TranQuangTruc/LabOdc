package com.TaskAndReporting_service.repository;

import com.TaskAndReporting_service.entity.LabAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabAdminRepository extends JpaRepository<LabAdmin, String> {
}
