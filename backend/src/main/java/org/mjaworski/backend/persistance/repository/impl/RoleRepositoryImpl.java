package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.Role;
import org.mjaworski.backend.persistance.repository.RoleRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private RoleJpaRepository roleJpaRepository;

    @Autowired
    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<Role> get(String name) {
        return roleJpaRepository.findByName(name);
    }
}
