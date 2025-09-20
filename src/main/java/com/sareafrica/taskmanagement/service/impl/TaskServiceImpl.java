package com.sareafrica.taskmanagement.service.impl;

import com.sareafrica.taskmanagement.dto.TaskDto;
import com.sareafrica.taskmanagement.entity.Status;
import com.sareafrica.taskmanagement.entity.Tag;
import com.sareafrica.taskmanagement.entity.Task;
import com.sareafrica.taskmanagement.mapper.TaskMapper;
import com.sareafrica.taskmanagement.repository.TagRepository;
import com.sareafrica.taskmanagement.repository.TaskRepository;
import com.sareafrica.taskmanagement.service.TaskService;
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

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = TaskMapper.mapToTask(taskDto);

        // Default status if none is provided
        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }

        // Handle tags
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

        // Update tags
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
        return TaskMapper.mapToTaskDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        taskRepository.delete(task);
    }
}
