package com.labodc.admin_service.repository;

import com.labodc.admin_service.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TalentRepository extends JpaRepository<Talent, UUID> {
}
