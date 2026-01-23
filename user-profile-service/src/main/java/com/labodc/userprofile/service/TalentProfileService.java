package com.labodc.userprofile.service;

import com.labodc.userprofile.entity.profile.TalentProfile;
import com.labodc.userprofile.messaging.NotificationMessage;
import com.labodc.userprofile.messaging.NotificationPublisher;
import com.labodc.userprofile.repository.profile.TalentProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TalentProfileService {

    private final TalentProfileRepository repo;
    private final NotificationPublisher notificationPublisher;

    public TalentProfileService(TalentProfileRepository repo, NotificationPublisher notificationPublisher) {
        this.repo = repo;
        this.notificationPublisher = notificationPublisher;
    }

    @Transactional
    public TalentProfile create(TalentProfile input) {
        if (input.getUserId() == null) throw new IllegalArgumentException("userId is required");
        if (repo.existsByUserId(input.getUserId())) throw new IllegalArgumentException("Talent profile already exists");

        TalentProfile saved = repo.save(input);

        notificationPublisher.send(new NotificationMessage(
                saved.getUserId(),
                "Talent profile created",
                "Your talent profile has been created.",
                "PROFILE_CREATED"
        ));
        return saved;
    }

    @Transactional(readOnly = true)
    public TalentProfile getByUserId(Long userId) {
        return repo.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Talent profile not found"));
    }

    @Transactional
    public TalentProfile update(Long userId, TalentProfile patch) {
        TalentProfile p = getByUserId(userId);
        if (patch.getFullName() != null) p.setFullName(patch.getFullName());
        if (patch.getSkills() != null) p.setSkills(patch.getSkills());
        if (patch.getPortfolioUrl() != null) p.setPortfolioUrl(patch.getPortfolioUrl());
        return repo.save(p);
    }
}
