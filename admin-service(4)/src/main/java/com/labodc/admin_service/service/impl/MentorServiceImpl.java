package com.labodc.admin_service.service.impl;

import com.labodc.admin_service.entity.Mentor;
import com.labodc.admin_service.entity.MentorStatus;
import com.labodc.admin_service.exception.NotFoundException;
import com.labodc.admin_service.repository.MentorRepository;
import com.labodc.admin_service.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository repo;

    @Override
    public List<Mentor> getAll() {
        return repo.findAll();
    }

    @Override
    public Mentor getById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Mentor not found"));
    }

    @Override
    public void suspend(UUID id) {
        Mentor m = getById(id);
        m.setStatus(MentorStatus.SUSPENDED);
        repo.save(m);
    }

    @Override
    public void activate(UUID id) {
        Mentor m = getById(id);
        m.setStatus(MentorStatus.ACTIVE);
        repo.save(m);
    }
}
