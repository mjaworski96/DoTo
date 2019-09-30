package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Task;

import java.util.Optional;

public interface TaskRepository {
    Optional<Task> get(int id);
    void save(Task task);
    void delete(Task task);
}
