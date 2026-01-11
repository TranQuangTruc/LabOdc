package com.labodc.admin_service.service;

import com.labodc.admin_service.entity.Talent;
import java.util.List;
import java.util.UUID;

public interface TalentService {

    List<Talent> getAll();

    Talent getById(UUID id);

    void suspend(UUID id);

    void activate(UUID id);
}
