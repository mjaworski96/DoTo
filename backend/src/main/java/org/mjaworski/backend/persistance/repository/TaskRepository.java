package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> get(int id);
    void save(Task task);
    void delete(Task task);
    List<Task> getAllToDo(int userId);
    List<Task> getAllInProgress(int userId);
}
