package com.labodc.userprofile.controller;


import com.labodc.userprofile.service.EnterpriseProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/enterprises")
@PreAuthorize("hasRole('LAB_ADMIN')")
public class AdminEnterpriseController {


private final EnterpriseProfileService service;


public AdminEnterpriseController(EnterpriseProfileService service) {
this.service = service;
}


@GetMapping
public ResponseEntity<?> list() {
return ResponseEntity.ok(service.getAll());
}


@PutMapping("/{id}/verify")
public ResponseEntity<?> verify(@PathVariable Long id) {
service.verify(id);
return ResponseEntity.ok("Verified");
}


@PutMapping("/{id}/reject")
public ResponseEntity<?> reject(@PathVariable Long id,
@RequestParam String reason) {
service.reject(id, reason);
return ResponseEntity.ok("Rejected");
}
}