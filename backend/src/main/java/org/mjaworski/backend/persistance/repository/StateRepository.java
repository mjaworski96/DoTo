package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.State;

import java.util.Optional;

public interface StateRepository {
    Optional<State> get(String stateName);
}
