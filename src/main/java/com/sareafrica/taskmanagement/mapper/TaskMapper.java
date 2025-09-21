package com.sareafrica.taskmanagement.mapper;

import com.sareafrica.taskmanagement.dto.TaskDto;
import com.sareafrica.taskmanagement.entity.Status;
import com.sareafrica.taskmanagement.entity.Tag;
import com.sareafrica.taskmanagement.entity.Task;

import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDto mapToTaskDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus().name(),
                task.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),
                task.getGoogleEventId()
        );
    }

    public static Task mapToTask(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(Status.valueOf(dto.getStatus()));
        task.setGoogleEventId(dto.getGoogleEventId());
        // Tags will be handled in service layer (to check if they exist or need to be created)
        return task;
    }
}
