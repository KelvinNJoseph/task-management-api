package com.sareafrica.taskmanagement.mapper;

import com.sareafrica.taskmanagement.dto.TagDto;
import com.sareafrica.taskmanagement.entity.Tag;

public class TagMapper {
    public static TagDto mapToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), tag.getTasks().size());
    }

    public static Tag mapToTag(TagDto dto) {
        return new Tag(dto.getId(), dto.getName(), null);
    }
}
