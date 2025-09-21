package com.sareafrica.taskmanagement.service;

import com.sareafrica.taskmanagement.dto.TaskDto;
import com.sareafrica.taskmanagement.entity.Task;
import com.sareafrica.taskmanagement.mapper.TaskMapper;
import com.sareafrica.taskmanagement.repository.TagRepository;
import com.sareafrica.taskmanagement.repository.TaskRepository;
import com.sareafrica.taskmanagement.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TagRepository tagRepository;
    @Mock private GoogleCalendarService googleCalendarService;

    @InjectMocks private TaskServiceImpl taskService;

    @Test
    void createTask_shouldSaveAndReturnDto() {

        TaskDto input = new TaskDto(null, "Test Task", "Description",
                LocalDateTime.now(), "PENDING", Set.of("Tag1"), null);

        Task saved = TaskMapper.mapToTask(input);
        saved.setId(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(saved);


        TaskDto result = taskService.createTask(input);


        assertNotNull(result.getId());
        assertEquals("Test Task", result.getTitle());
    }
}
