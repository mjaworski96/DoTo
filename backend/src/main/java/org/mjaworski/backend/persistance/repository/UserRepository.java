package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getByUsername(String username);
}
