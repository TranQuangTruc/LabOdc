package com.labodc.admin_service.controller;

import com.labodc.admin_service.entity.Mentor;
import com.labodc.admin_service.service.MentorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/mentors")
public class MentorAdminController {

    private final MentorService service;
    public MentorAdminController(MentorService service) { this.service = service; }

    @GetMapping
    public List<Mentor> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Mentor getDetail(@PathVariable UUID id) { return service.getById(id); }

    @PostMapping("/{id}/suspend")
    public void suspend(@PathVariable UUID id) { service.suspend(id); }

    @PostMapping("/{id}/activate")
    public void activate(@PathVariable UUID id) { service.activate(id); }
}
