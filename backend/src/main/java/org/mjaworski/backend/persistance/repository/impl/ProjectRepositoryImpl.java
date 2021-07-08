package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.Project;
import org.mjaworski.backend.persistance.repository.ProjectRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.ProjectJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    private ProjectJpaRepository projectJpaRepository;

    public ProjectRepositoryImpl(ProjectJpaRepository projectJpaRepository) {
        this.projectJpaRepository = projectJpaRepository;
    }

    @Override
    public Optional<Project> get(int id) {
        return projectJpaRepository.findById(id);
    }

    @Override
    public List<Project> get(int userId, boolean archived, Pageable pageable) {
        return projectJpaRepository.get(userId, archived, pageable);
    }

    @Override
    public int getCount(int userId, boolean archived) {
        return projectJpaRepository.getCount(userId, archived);
    }

    @Override
    public void save(Project project) {
        projectJpaRepository.save(project);
    }

    @Override
    public void delete(Project project) {
        projectJpaRepository.delete(project);
    }
}
