package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
