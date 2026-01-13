package com.labodc.userprofile.service;

import com.labodc.userprofile.entity.profile.TalentProfile;
import com.labodc.userprofile.repository.profile.TalentProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class TalentProfileService {

    private final TalentProfileRepository repository;

    public TalentProfileService(TalentProfileRepository repository) {
        this.repository = repository;
    }

    // Lay profile dau tien, neu chua co thi tao moi
    public TalentProfile getOrCreateDefault() {
        return repository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    TalentProfile profile = new TalentProfile();
                    profile.setFullName("");
                    profile.setEmail("");
                    profile.setSkills("");
                    profile.setPortfolioUrl("");
                    return repository.save(profile);
                });
    }

    // Luu profile
    public TalentProfile save(TalentProfile profile) {
        return repository.save(profile);
    }
}