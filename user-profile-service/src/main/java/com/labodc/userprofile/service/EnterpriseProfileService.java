package com.labodc.userprofile.service;

import com.labodc.userprofile.entity.profile.EnterpriseProfile;
import com.labodc.userprofile.messaging.NotificationMessage;
import com.labodc.userprofile.messaging.NotificationPublisher;
import com.labodc.userprofile.repository.profile.EnterpriseProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnterpriseProfileService {

    private final EnterpriseProfileRepository repo;
    private final NotificationPublisher notificationPublisher;

    public EnterpriseProfileService(EnterpriseProfileRepository repo, NotificationPublisher notificationPublisher) {
        this.repo = repo;
        this.notificationPublisher = notificationPublisher;
    }

    @Transactional
    public EnterpriseProfile create(EnterpriseProfile input) {
        if (input.getUserId() == null) throw new IllegalArgumentException("userId is required");
        if (repo.existsByUserId(input.getUserId())) throw new IllegalArgumentException("Enterprise profile already exists");

        EnterpriseProfile saved = repo.save(input);

        notificationPublisher.send(new NotificationMessage(
                saved.getUserId(),
                "Enterprise profile created",
                "Your enterprise profile has been created and is pending verification.",
                "PROFILE_CREATED"
        ));
        return saved;
    }

    @Transactional(readOnly = true)
    public EnterpriseProfile getByUserId(Long userId) {
        return repo.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Enterprise profile not found"));
    }

    @Transactional
    public EnterpriseProfile update(Long userId, EnterpriseProfile patch) {
        EnterpriseProfile p = getByUserId(userId);

        if (patch.getCompanyName() != null) p.setCompanyName(patch.getCompanyName());
        if (patch.getTaxCode() != null) p.setTaxCode(patch.getTaxCode());
        if (patch.getDescription() != null) p.setDescription(patch.getDescription());
        if (patch.getWebsite() != null) p.setWebsite(patch.getWebsite());

        return repo.save(p);
    }

    @Transactional
    public EnterpriseProfile verify(Long userId, boolean verified, String rejectedReason) {
        EnterpriseProfile p = getByUserId(userId);
        p.setVerified(verified);
        p.setRejectedReason(verified ? null : rejectedReason);

        EnterpriseProfile saved = repo.save(p);

        notificationPublisher.send(new NotificationMessage(
                saved.getUserId(),
                verified ? "Enterprise verified" : "Enterprise rejected",
                verified ? "Your enterprise profile has been verified." : ("Your enterprise profile was rejected: " + rejectedReason),
                verified ? "PROFILE_VERIFIED" : "PROFILE_REJECTED"
        ));

        return saved;
    }
}
