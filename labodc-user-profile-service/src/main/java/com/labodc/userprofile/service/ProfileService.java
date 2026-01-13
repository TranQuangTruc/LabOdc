package com.labodc.userprofile.service;


import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.entity.profile.*;
import com.labodc.userprofile.repository.UserRepository;
import com.labodc.userprofile.repository.profile.*;
import org.springframework.stereotype.Service;


@Service
public class ProfileService {


private final UserRepository userRepository;
private final TalentProfileRepository talentRepo;
private final MentorProfileRepository mentorRepo;
private final EnterpriseProfileRepository enterpriseRepo;


public ProfileService(UserRepository userRepository,
TalentProfileRepository talentRepo,
MentorProfileRepository mentorRepo,
EnterpriseProfileRepository enterpriseRepo) {
this.userRepository = userRepository;
this.talentRepo = talentRepo;
this.mentorRepo = mentorRepo;
this.enterpriseRepo = enterpriseRepo;
}


// Tao profile theo role khi user dang ky
public void createProfileForUser(User user) {
switch (user.getRole()) {
case TALENT -> {
TalentProfile profile = new TalentProfile();
profile.setUser(user);
talentRepo.save(profile);
}
case MENTOR -> {
MentorProfile profile = new MentorProfile();
profile.setUser(user);
mentorRepo.save(profile);
}
case ENTERPRISE -> {
EnterpriseProfile profile = new EnterpriseProfile();
profile.setUser(user);
profile.setVerified(false);
enterpriseRepo.save(profile);
}
default -> {}
}
}
}