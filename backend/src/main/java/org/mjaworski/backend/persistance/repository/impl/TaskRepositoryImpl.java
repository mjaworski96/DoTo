package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.Task;
import org.mjaworski.backend.persistance.repository.TaskRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.TaskJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private TaskJpaRepository taskJpaRepository;

    @Autowired
    public TaskRepositoryImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Optional<Task> get(int id) {
        return taskJpaRepository.findById(id);
    }

    @Override
    public void save(Task task) {
        taskJpaRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskJpaRepository.delete(task);
    }
}
