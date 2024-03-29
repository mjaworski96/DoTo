package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM User u ORDER BY u.id")
    List<User> get(Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u")
    int getCount();

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findById(int id);
}
