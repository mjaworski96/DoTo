package org.mjaworski.backend.security;

import org.mjaworski.backend.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountAuthenticationService implements UserDetailsService  {

    private UserRepository userRepository;

    @Autowired
    public AccountAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<org.mjaworski.backend.persistance.entity.User> account = userRepository
                .getByUsername(username);

        if (account.isPresent()) {
            return User
                    .withUsername(account.get().getUsername())
                    .password(account.get().getPassword())
                    .roles(getUserRoles(account.get()))
                    .build();
        } else {
            throw new UsernameNotFoundException("Could not find user "
                    + username);
        }
    }
    private String[] getUserRoles(org.mjaworski.backend.persistance.entity.User user) {
        return user.getRoles()
                .stream()
                .map(item -> item.getName())
                .toArray(String[]::new);
    }
}
