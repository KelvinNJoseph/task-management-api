package com.sareafrica.taskmanagement.repository;

import com.sareafrica.taskmanagement.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // Example custom query later:
    // Optional<Tag> findByName(String name);
}
