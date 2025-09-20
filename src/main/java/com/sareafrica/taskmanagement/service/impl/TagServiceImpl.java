package com.sareafrica.taskmanagement.service.impl;

import com.sareafrica.taskmanagement.dto.TagDto;
import com.sareafrica.taskmanagement.entity.Tag;
import com.sareafrica.taskmanagement.mapper.TagMapper;
import com.sareafrica.taskmanagement.repository.TagRepository;
import com.sareafrica.taskmanagement.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(Long id) {
        return tagRepository.findById(id)
                .map(TagMapper::mapToTagDto)
                .orElse(null);
    }
}
