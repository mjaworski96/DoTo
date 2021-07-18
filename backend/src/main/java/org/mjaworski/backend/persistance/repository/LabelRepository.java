package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository {
    void save(Label labelEntity);

    List<Label> getAll(int projectId);

    Optional<Label> get(int id);

    void delete(Label label);
}
