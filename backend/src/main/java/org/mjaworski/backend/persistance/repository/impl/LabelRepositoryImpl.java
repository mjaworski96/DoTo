package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.Label;
import org.mjaworski.backend.persistance.repository.LabelRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.LabelJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LabelRepositoryImpl implements LabelRepository {
    private LabelJpaRepository labelJpaRepository;

    public LabelRepositoryImpl(LabelJpaRepository labelJpaRepository) {
        this.labelJpaRepository = labelJpaRepository;
    }

    @Override
    public void save(Label labelEntity) {
        labelJpaRepository.save(labelEntity);
    }

    @Override
    public List<Label> getAll(int projectId) {
        return labelJpaRepository.findAllByProjectIdOrderByIdAsc(projectId);
    }

    @Override
    public Optional<Label> get(int id) {
        return labelJpaRepository.findById(id);
    }

    @Override
    public void delete(Label label) {
        labelJpaRepository.delete(label);
    }
}
