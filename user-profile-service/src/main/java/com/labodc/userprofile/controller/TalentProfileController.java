package com.labodc.userprofile.controller;

import com.labodc.userprofile.entity.profile.TalentProfile;
import com.labodc.userprofile.service.TalentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/talent")
public class TalentProfileController {

    private final TalentProfileService service;

    public TalentProfileController(TalentProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TalentProfile> create(@RequestBody TalentProfile body) {
        return ResponseEntity.ok(service.create(body));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<TalentProfile> get(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<TalentProfile> update(@PathVariable Long userId, @RequestBody TalentProfile body) {
        return ResponseEntity.ok(service.update(userId, body));
    }
}
