package com.labodc.userprofile.controller;

import com.labodc.userprofile.entity.profile.EnterpriseProfile;
import com.labodc.userprofile.service.EnterpriseProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/enterprise")
public class EnterpriseProfileController {

    private final EnterpriseProfileService service;

    public EnterpriseProfileController(EnterpriseProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnterpriseProfile> create(@RequestBody EnterpriseProfile body) {
        return ResponseEntity.ok(service.create(body));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EnterpriseProfile> get(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EnterpriseProfile> update(@PathVariable Long userId, @RequestBody EnterpriseProfile body) {
        return ResponseEntity.ok(service.update(userId, body));
    }

    @PutMapping("/{userId}/verify")
    public ResponseEntity<EnterpriseProfile> verify(
            @PathVariable Long userId,
            @RequestParam boolean verified,
            @RequestParam(required = false) String rejectedReason
    ) {
        return ResponseEntity.ok(service.verify(userId, verified, rejectedReason));
    }
}
