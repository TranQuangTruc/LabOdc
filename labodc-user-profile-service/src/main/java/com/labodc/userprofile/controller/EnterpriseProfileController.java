package com.labodc.userprofile.controller;

import com.labodc.userprofile.entity.profile.EnterpriseProfile;
import com.labodc.userprofile.service.EnterpriseProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/profile")
public class EnterpriseProfileController {

    private final EnterpriseProfileService service;

    public EnterpriseProfileController(EnterpriseProfileService service) {
        this.service = service;
    }

    // Enterprise tu cap nhat profile
    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal UserDetails user,
                                    @RequestBody EnterpriseProfile data) {
        return ResponseEntity.ok(
                service.update(user.getUsername(), data)
        );
    }
}
