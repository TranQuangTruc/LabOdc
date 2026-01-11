package com.labodc.admin_service.service;

import com.labodc.admin_service.entity.Mentor;

import java.util.List;
import java.util.UUID;

public interface MentorService {

    List<Mentor> getAll();

    Mentor getById(UUID id);

    void suspend(UUID id);

    void activate(UUID id);
}
