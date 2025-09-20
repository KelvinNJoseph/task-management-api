package com.sareafrica.taskmanagement.repository;

import com.sareafrica.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Example custom query later:
    // List<Task> findByStatus(Status status);
}
