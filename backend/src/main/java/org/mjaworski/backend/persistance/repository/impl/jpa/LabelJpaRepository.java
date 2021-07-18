package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelJpaRepository extends JpaRepository<Label, Integer> {

    List<Label> findAllByProjectId(int projectId);

}
