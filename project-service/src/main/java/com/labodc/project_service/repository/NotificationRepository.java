package com.labodc.project_service.repository;
import com.labodc.project_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> { }