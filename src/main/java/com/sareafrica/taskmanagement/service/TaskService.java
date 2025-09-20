package com.sareafrica.taskmanagement.service;

import com.sareafrica.taskmanagement.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);
    TaskDto getTaskById(Long id);
    List<TaskDto> getAllTasks();
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
}
