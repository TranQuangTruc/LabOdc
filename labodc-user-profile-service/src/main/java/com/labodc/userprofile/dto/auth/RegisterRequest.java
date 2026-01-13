package com.labodc.userprofile.dto.auth;


import com.labodc.userprofile.entity.UserRole;


public class RegisterRequest {


private String username;
private String password;
private UserRole role;


public String getUsername() {
return username;
}


public String getPassword() {
return password;
}


public UserRole getRole() {
return role;
}
}