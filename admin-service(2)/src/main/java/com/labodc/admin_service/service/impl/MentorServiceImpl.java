package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.*;
import com.labodc.admin_service.repository.*;
import com.labodc.admin_service.service.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MentorServiceImpl implements MentorService {

    private final MentorRepository repo;
    public MentorServiceImpl(MentorRepository repo) { this.repo = repo; }

    public List<Mentor> getAll() { return repo.findAll(); }
    public Mentor getById(UUID id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Mentor not found")); }
    public void suspend(UUID id) {
        Mentor m = getById(id);
        m.setStatus(MentorStatus.SUSPENDED);
        repo.save(m);
    }
    public void activate(UUID id) {
        Mentor m = getById(id);
        m.setStatus(MentorStatus.ACTIVE);
        repo.save(m);
    }
}
