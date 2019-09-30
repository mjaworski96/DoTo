package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.State;
import org.mjaworski.backend.persistance.repository.StateRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.StateJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StateRepositoryImpl implements StateRepository {
    private StateJpaRepository stateJpaRepository;

    @Autowired
    public StateRepositoryImpl(StateJpaRepository stateJpaRepository) {
        this.stateJpaRepository = stateJpaRepository;
    }

    @Override
    public Optional<State> get(String stateName) {
        return stateJpaRepository.findByNameIgnoreCase(stateName);
    }
}
