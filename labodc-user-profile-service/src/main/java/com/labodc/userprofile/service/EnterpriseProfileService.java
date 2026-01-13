package com.labodc.userprofile.service;

import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.entity.profile.EnterpriseProfile;
import com.labodc.userprofile.repository.UserRepository;
import com.labodc.userprofile.repository.profile.EnterpriseProfileRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseProfileService {

    private final EnterpriseProfileRepository repo;
    private final UserRepository userRepo;

    public EnterpriseProfileService(EnterpriseProfileRepository repo,
                                    UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // Cho service khac hoi enterprise da verify chua
    @Cacheable(value = "enterprise-profile", key = "#username")
    public EnterpriseProfile getByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return repo.findByUserId(user.getId()).orElseThrow();
    }

    // Enterprise cap nhat profile
    @CacheEvict(value = "enterprise-profile", key = "#username")
    public EnterpriseProfile update(String username, EnterpriseProfile data) {
        User user = userRepo.findByUsername(username).orElseThrow();
        EnterpriseProfile profile = repo.findByUserId(user.getId()).orElseThrow();

        profile.setCompanyName(data.getCompanyName());
        profile.setTaxCode(data.getTaxCode());
        profile.setDescription(data.getDescription());
        profile.setWebsite(data.getWebsite());

        return repo.save(profile);
    }

    // LabAdmin xem tat ca enterprise
    public List<EnterpriseProfile> getAll() {
        return repo.findAll();
    }

    public void verify(Long id) {
        EnterpriseProfile p = repo.findById(id).orElseThrow();
        p.setVerified(true);
        p.setRejectedReason(null);
        repo.save(p);
    }

    public void reject(Long id, String reason) {
        EnterpriseProfile p = repo.findById(id).orElseThrow();
        p.setVerified(false);
        p.setRejectedReason(reason);
        repo.save(p);
    }
}
