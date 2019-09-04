package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userJpaRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }

    @Override
    public List<User> get(Pageable pageable) {
        return userJpaRepository.get(pageable);
    }

    @Override
    public int getCount() {
        return userJpaRepository.getCount();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userJpaRepository.findByEmailIgnoreCase(email);
    }
}
