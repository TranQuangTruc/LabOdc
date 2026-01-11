package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.Talent;
import com.labodc.admin_service.entity.TalentStatus;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.repository.TalentRepository;
import com.labodc.admin_service.service.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository repo;

    @Override
    public List<Talent> getAll() {
        return repo.findAll(); // trả về entity list luôn
    }

    @Override
    public Talent getById(UUID id) {
        return repo.findById(id)
                   .orElseThrow(() -> new NotFoundException("Talent not found"));
    }

    @Override
    public void suspend(UUID id) {
        Talent t = getById(id);
        t.setStatus(TalentStatus.SUSPENDED);
        repo.save(t);
    }

    @Override
    public void activate(UUID id) {
        Talent t = getById(id);
        t.setStatus(TalentStatus.ACTIVE);
        repo.save(t);
    }
}
