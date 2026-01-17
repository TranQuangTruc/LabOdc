package com.labodc.project_service.service.impl;

import com.labodc.project_service.dto.ApplyProjectRequest;
import com.labodc.project_service.dto.IdeaChangeRequest;
import com.labodc.project_service.dto.IdeaReviewRequest;
import com.labodc.project_service.dto.IdeaSubmitRequest;
import com.labodc.project_service.entity.Idea;
import com.labodc.project_service.entity.Notification;
import com.labodc.project_service.entity.ProjectApplication;
import com.labodc.project_service.repository.IdeaRepository;
import com.labodc.project_service.repository.NotificationRepository;
import com.labodc.project_service.repository.ProjectApplicationRepository;
import com.labodc.project_service.service.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {
    private final IdeaRepository ideaRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public Idea submitIdea(IdeaSubmitRequest request) {
        Idea idea = new Idea();
        idea.setTitle(request.getTitle());
        idea.setDescription(request.getDescription());
        idea.setOwnerId(request.getEnterpriseId());
        idea.setSpecialtyId(request.getSpecialtyId());
        idea.setStatus("PENDING");

        return ideaRepository.save(idea);
    }

    @Override
    public List<Idea> getPendingIdeas() {

        return ideaRepository.findAll().stream()
                .filter(i -> "PENDING".equals(i.getStatus()))
                .toList();
    }

    @Override
    @Transactional
    public Idea reviewIdea(String id, IdeaReviewRequest request) {

        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea không tồn tại"));

        idea.setStatus(request.getStatus());
        idea.setNote(request.getNote());
        idea.setUpdatedDate(LocalDateTime.now());
        Idea savedIdea = ideaRepository.save(idea);


        Notification notify = new Notification();
        notify.setUserId(idea.getOwnerId());
        notify.setProjectId(idea.getId());
        notify.setDescription("Dự án '" + idea.getTitle() + "' đã được: " + request.getStatus());
        notify.setRead(false);

        notificationRepository.save(notify);

        return savedIdea;
    }
    private final ProjectApplicationRepository applicationRepository;

    @Override
    public List<Idea> getApprovedProjects() {

        return ideaRepository.findAll().stream()
                .filter(idea -> "APPROVED".equals(idea.getStatus()))
                .toList();
    }

    @Override
    @Transactional
    public ProjectApplication applyToProject(ApplyProjectRequest request) {
        ProjectApplication application = new ProjectApplication();
        application.setProjectId(request.getProjectId());
        application.setTalentId(request.getTalentId());
        application.setMessage(request.getMessage());
        application.setStatus("PENDING");

        return applicationRepository.save(application);
    }
    @Override
    @Transactional
    public Idea requestChangeOrCancel(String id, IdeaChangeRequest request) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dự án không tồn tại"));


        if ("CANCEL".equalsIgnoreCase(request.getType())) {
            idea.setStatus("PENDING_CANCEL");
        } else {
            idea.setStatus("PENDING_CHANGE");
        }


        idea.setNote("Yêu cầu " + request.getType() + ": " + request.getReason());
        idea.setUpdatedDate(java.time.LocalDateTime.now());

        return ideaRepository.save(idea);
    }

    @Override
    @Transactional
    public Idea approveChangeOrCancel(String id, boolean isAccepted) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dự án không tồn tại"));

        String oldStatus = idea.getStatus();

        if (isAccepted) {
            if ("PENDING_CANCEL".equals(oldStatus)) {
                idea.setStatus("CANCELED");
            } else {
                idea.setStatus("APPROVED");
            }
        } else {

            idea.setStatus("APPROVED");
        }


        Notification notify = new Notification();
        notify.setUserId(idea.getOwnerId());
        notify.setProjectId(idea.getId());
        notify.setDescription("Yêu cầu " + oldStatus + " đã được Admin " + (isAccepted ? "CHẤP NHẬN" : "TỪ CHỐI"));
        notificationRepository.save(notify);

        return ideaRepository.save(idea);
    }
}