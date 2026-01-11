package com.labodc.project_service.service;

import com.labodc.project_service.dto.ApplyProjectRequest;
import com.labodc.project_service.dto.IdeaReviewRequest;
import com.labodc.project_service.dto.IdeaSubmitRequest;
import com.labodc.project_service.entity.Idea;
import com.labodc.project_service.entity.ProjectApplication;

import java.util.List;

public interface IdeaService {
    Idea submitIdea(IdeaSubmitRequest request);
    Idea reviewIdea(String id, IdeaReviewRequest request);
    List<Idea> getPendingIdeas();
    List<Idea> getApprovedProjects();
    ProjectApplication applyToProject(ApplyProjectRequest request);
}
