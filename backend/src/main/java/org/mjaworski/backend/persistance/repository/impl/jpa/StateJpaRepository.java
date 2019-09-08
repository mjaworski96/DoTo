package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateJpaRepository extends JpaRepository<State, Integer> {
    Optional<State> findByNameIgnoreCase(String name);
}
