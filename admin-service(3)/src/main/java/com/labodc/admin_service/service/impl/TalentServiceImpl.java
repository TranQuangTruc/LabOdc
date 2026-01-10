package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.*;
import com.labodc.admin_service.repository.*;
import com.labodc.admin_service.service.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TalentServiceImpl implements TalentService {

    private final TalentRepository repo;
    public TalentServiceImpl(TalentRepository repo) { this.repo = repo; }

    public List<Talent> getAll() { return repo.findAll(); }
    public Talent getById(UUID id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Talent not found")); }
    public void suspend(UUID id) {
        Talent t = getById(id);
        t.setStatus(TalentStatus.SUSPENDED);
        repo.save(t);
    }
    public void activate(UUID id) {
        Talent t = getById(id);
        t.setStatus(TalentStatus.ACTIVE);
        repo.save(t);
    }
}
