package com.labodc.userprofile.controller;


import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {


private final UserRepository userRepository;


public UserController(UserRepository userRepository) {
this.userRepository = userRepository;
}


// Lay danh sach tat ca user, chi admin duoc phep truy cap
@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<User>> getAllUsers() {
return ResponseEntity.ok(userRepository.findAll());
}


// Lay thong tin user theo id
@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN','ENTERPRISE','TALENT','MENTOR')")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
return userRepository.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}
}