package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> get(int id);
    List<Project> get(String username, Pageable pageable);
    int getCount(String username);
    void save(Project project);
    void delete(Project project);
}