package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> get(String name);
}
