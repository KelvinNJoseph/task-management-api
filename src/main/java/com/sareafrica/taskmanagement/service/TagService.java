package com.sareafrica.taskmanagement.service;

import com.sareafrica.taskmanagement.dto.TagDto;
import java.util.List;

public interface TagService {
    List<TagDto> getAllTags();
    TagDto getTagById(Long id);
}
