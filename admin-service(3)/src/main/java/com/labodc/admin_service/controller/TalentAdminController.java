package com.labodc.admin_service.controller;

import com.labodc.admin_service.entity.Talent;
import com.labodc.admin_service.service.TalentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/talents")
public class TalentAdminController {

    private final TalentService service;
    public TalentAdminController(TalentService service) { this.service = service; }

    @GetMapping
    public List<Talent> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Talent getDetail(@PathVariable UUID id) { return service.getById(id); }

    @PostMapping("/{id}/suspend")
    public void suspend(@PathVariable UUID id) { service.suspend(id); }

    @PostMapping("/{id}/activate")
    public void activate(@PathVariable UUID id) { service.activate(id); }
}
