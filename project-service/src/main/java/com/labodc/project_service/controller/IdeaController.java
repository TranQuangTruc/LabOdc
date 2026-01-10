package com.labodc.project_service.controller;

import com.labodc.project_service.dto.IdeaReviewRequest;
import com.labodc.project_service.dto.IdeaSubmitRequest;
import com.labodc.project_service.entity.Idea;
import com.labodc.project_service.service.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ideas")
@RequiredArgsConstructor
public class IdeaController {
    private final IdeaService ideaService;

    @PostMapping("/submit")
    public ResponseEntity<Idea> submit(@RequestBody IdeaSubmitRequest request) {
        return ResponseEntity.ok(ideaService.submitIdea(request));
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<Idea> review(@PathVariable String id, @RequestBody IdeaReviewRequest request) {
        return ResponseEntity.ok(ideaService.reviewIdea(id, request));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Idea>> getPending() {
        return ResponseEntity.ok(ideaService.getPendingIdeas());
    }
}
