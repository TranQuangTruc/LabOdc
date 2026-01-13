package com.labodc.userprofile.controller;


import com.labodc.userprofile.entity.profile.*;
import com.labodc.userprofile.repository.profile.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {


private final TalentProfileRepository talentRepo;
private final MentorProfileRepository mentorRepo;
private final EnterpriseProfileRepository enterpriseRepo;


public ProfileController(TalentProfileRepository talentRepo,
MentorProfileRepository mentorRepo,
EnterpriseProfileRepository enterpriseRepo) {
this.talentRepo = talentRepo;
this.mentorRepo = mentorRepo;
this.enterpriseRepo = enterpriseRepo;
}


// Lay profile cua user dang dang nhap
@GetMapping("/me/talent")
public ResponseEntity<?> myTalentProfile(@AuthenticationPrincipal UserDetails user) {
return ResponseEntity.ok(talentRepo.findByUserId(Long.parseLong(user.getUsername())));
}
}