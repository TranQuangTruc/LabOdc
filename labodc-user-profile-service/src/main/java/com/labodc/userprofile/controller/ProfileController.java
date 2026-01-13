package com.labodc.userprofile.controller;

import com.labodc.userprofile.entity.profile.TalentProfile;
import com.labodc.userprofile.service.TalentProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/me/talent")
@CrossOrigin
public class ProfileController {

    private final TalentProfileService service;

    public ProfileController(TalentProfileService service) {
        this.service = service;
    }

    @GetMapping
    public TalentProfile getProfile() {
        return service.getOrCreateDefault();
    }

    @PutMapping
    public TalentProfile updateProfile(@RequestBody TalentProfile profile) {
        return service.save(profile);
    }
}
