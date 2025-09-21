package com.sareafrica.taskmanagement.service.impl;

import com.sareafrica.taskmanagement.dto.TaskDto;
import com.sareafrica.taskmanagement.entity.Status;
import com.sareafrica.taskmanagement.entity.Tag;
import com.sareafrica.taskmanagement.entity.Task;
import com.sareafrica.taskmanagement.mapper.TaskMapper;
import com.sareafrica.taskmanagement.repository.TagRepository;
import com.sareafrica.taskmanagement.repository.TaskRepository;
import com.sareafrica.taskmanagement.service.TaskService;
import com.sareafrica.taskmanagement.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final GoogleCalendarService googleCalendarService;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = TaskMapper.mapToTask(taskDto);

        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }

        Set<Tag> tags = new HashSet<>();
        if (taskDto.getTags() != null) {
            for (String tagName : taskDto.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(null, tagName, new HashSet<>())));
                tags.add(tag);
            }
        }
        task.setTags(tags);

        Task savedTask = taskRepository.save(task);

        try {
            String googleEventId = googleCalendarService.createEvent(
                    savedTask.getTitle(),
                    savedTask.getDescription(),
                    savedTask.getDueDate(),
                    savedTask.getDueDate().plusHours(1)
            );
            savedTask.setGoogleEventId(googleEventId);
            savedTask = taskRepository.save(savedTask);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to sync with Google Calendar: " + e.getMessage());
        }

        return TaskMapper.mapToTaskDto(savedTask);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        return TaskMapper.mapToTaskDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::mapToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(Status.valueOf(taskDto.getStatus()));

        Set<Tag> tags = new HashSet<>();
        if (taskDto.getTags() != null) {
            for (String tagName : taskDto.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(null, tagName, new HashSet<>())));
                tags.add(tag);
            }
        }
        task.setTags(tags);

        Task updatedTask = taskRepository.save(task);

        if (updatedTask.getGoogleEventId() != null) {
            try {
                googleCalendarService.updateEvent(
                        updatedTask.getGoogleEventId(),
                        updatedTask.getTitle(),
                        updatedTask.getDescription(),
                        updatedTask.getDueDate(),
                        updatedTask.getDueDate().plusHours(1)
                );
            } catch (Exception e) {
                System.err.println("⚠️ Failed to update Google Calendar event: " + e.getMessage());
            }
        }
        return TaskMapper.mapToTaskDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        if (task.getGoogleEventId() != null) {
            try {
                googleCalendarService.deleteEvent(task.getGoogleEventId());
            } catch (Exception e) {
                System.err.println("⚠️ Failed to delete Google Calendar event: " + e.getMessage());
            }
        }

        taskRepository.delete(task);
    }
}
