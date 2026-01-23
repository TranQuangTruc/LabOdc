package com.TaskAndReporting_service.service;

import com.TaskAndReporting_service.dto.request.ProjectTaskRequest;
import com.TaskAndReporting_service.dto.response.ProjectTaskResponse;
import com.TaskAndReporting_service.entity.ProjectTask;
import com.TaskAndReporting_service.mapper.ProjectTaskMapper;
import com.TaskAndReporting_service.repository.ProjectTaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectTaskService {
    ProjectTaskRepository projectTaskRepository;
    ProjectTaskMapper projectTaskMapper;

    public ProjectTaskResponse creatTask(ProjectTaskRequest request){
        ProjectTask projectTask = projectTaskMapper.toProjectTask(request);
        projectTask.setCreateAt(Instant.now());
        return projectTaskMapper.toProjectTaskResponse(projectTaskRepository.save(projectTask));
    }
    public List<ProjectTaskResponse> getMyTask(String projectId, String talentId){
        List<ProjectTask> byProjectIdAndTalentId =
                projectTaskRepository.findByProjectIdAndTalentIdsContaining(projectId, talentId);
        return byProjectIdAndTalentId.stream().map(projectTaskMapper::toProjectTaskResponse).toList();
    }
    public List<ProjectTaskResponse> getMyTaskForMentor(String projectId){
        List<ProjectTask> byProjectId = projectTaskRepository.findByProjectId(projectId);
        return byProjectId.stream().map(projectTaskMapper::toProjectTaskResponse).toList();
    }
}
