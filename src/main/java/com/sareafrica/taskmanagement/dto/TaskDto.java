package com.sareafrica.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
    private Set<String> tags;
}
