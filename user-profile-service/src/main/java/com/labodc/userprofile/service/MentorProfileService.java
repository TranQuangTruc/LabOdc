package com.labodc.userprofile.service;

import com.labodc.userprofile.entity.profile.MentorProfile;
import com.labodc.userprofile.messaging.NotificationMessage;
import com.labodc.userprofile.messaging.NotificationPublisher;
import com.labodc.userprofile.repository.profile.MentorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorProfileService {

    private final MentorProfileRepository repo;
    private final NotificationPublisher notificationPublisher;

    public MentorProfileService(MentorProfileRepository repo, NotificationPublisher notificationPublisher) {
        this.repo = repo;
        this.notificationPublisher = notificationPublisher;
    }

    @Transactional
    public MentorProfile create(MentorProfile input) {
        if (input.getUserId() == null) throw new IllegalArgumentException("userId is required");
        if (repo.existsByUserId(input.getUserId())) throw new IllegalArgumentException("Mentor profile already exists");

        MentorProfile saved = repo.save(input);

        notificationPublisher.send(new NotificationMessage(
                saved.getUserId(),
                "Mentor profile created",
                "Your mentor profile has been created.",
                "PROFILE_CREATED"
        ));
        return saved;
    }

    @Transactional(readOnly = true)
    public MentorProfile getByUserId(Long userId) {
        return repo.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Mentor profile not found"));
    }

    @Transactional
    public MentorProfile update(Long userId, MentorProfile patch) {
        MentorProfile p = getByUserId(userId);
        if (patch.getFullName() != null) p.setFullName(patch.getFullName());
        if (patch.getExpertise() != null) p.setExpertise(patch.getExpertise());
        if (patch.getCompany() != null) p.setCompany(patch.getCompany());
        return repo.save(p);
    }
}
