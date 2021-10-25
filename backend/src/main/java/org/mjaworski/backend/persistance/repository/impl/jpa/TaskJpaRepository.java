package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskJpaRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProjectOwnerIdAndStateNameAndProjectArchived(int id, String name, boolean archived);
}
