package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getByUsername(String username);
    Optional<User> getById(int userId);
    void save(User user);
    void delete(User user);
    List<User> get(Pageable pageable);
    int getCount();
    Optional<User> getByEmail(String email);
}
