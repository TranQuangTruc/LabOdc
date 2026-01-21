package com.labodc.project_service.controller;

import com.labodc.project_service.dto.ApplyProjectRequest;
import com.labodc.project_service.dto.IdeaChangeRequest;
import com.labodc.project_service.dto.IdeaReviewRequest;
import com.labodc.project_service.dto.IdeaSubmitRequest;
import com.labodc.project_service.entity.Idea;
import com.labodc.project_service.entity.ProjectApplication;
import com.labodc.project_service.service.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ideas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
    @GetMapping("/approved")
    public ResponseEntity<List<Idea>> getApproved() {
        return ResponseEntity.ok(ideaService.getApprovedProjects());
    }

    @PostMapping("/apply")
    public ResponseEntity<ProjectApplication> apply(@RequestBody ApplyProjectRequest request) {
        return ResponseEntity.ok(ideaService.applyToProject(request));
    }
    @PutMapping("/{id}/request-change")
    public ResponseEntity<Idea> requestChange(@PathVariable String id, @RequestBody IdeaChangeRequest request) {
        return ResponseEntity.ok(ideaService.requestChangeOrCancel(id, request));
    }
    
    @PutMapping("/{id}/review-change")
    public ResponseEntity<Idea> reviewChange(@PathVariable String id, @RequestParam boolean isAccepted) {
        return ResponseEntity.ok(ideaService.approveChangeOrCancel(id, isAccepted));
    }
    @PutMapping("/{id}/accept")
    public ResponseEntity<Idea> acceptProject(@PathVariable String id) {
        return ResponseEntity.ok(ideaService.approveChangeOrCancel(id, true));
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> rejectProject(@PathVariable String id) {
        ideaService.approveChangeOrCancel(id, false);
        return ResponseEntity.ok().build();
    }

}
