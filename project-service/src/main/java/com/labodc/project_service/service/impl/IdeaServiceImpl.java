package com.labodc.project_service.service.impl;

import com.labodc.project_service.dto.IdeaReviewRequest;
import com.labodc.project_service.dto.IdeaSubmitRequest;
import com.labodc.project_service.entity.Idea;
import com.labodc.project_service.entity.Notification;
import com.labodc.project_service.repository.IdeaRepository;
import com.labodc.project_service.repository.NotificationRepository;
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
        // Logic để Enterprise gửi dự án mới
        Idea idea = new Idea();
        idea.setTitle(request.getTitle());
        idea.setDescription(request.getDescription());
        idea.setOwnerId(request.getEnterpriseId()); // Lưu ID của Enterprise
        idea.setSpecialtyId(request.getSpecialtyId());
        idea.setStatus("PENDING"); // Trạng thái mặc định là chờ duyệt

        return ideaRepository.save(idea);
    }

    @Override
    public List<Idea> getPendingIdeas() {
        // LabAdmin lấy danh sách dự án đang chờ duyệt
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
}