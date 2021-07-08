package org.mjaworski.backend.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.mjaworski.backend.dto.user.PasswordChangeDto;
import org.mjaworski.backend.dto.user.ResetPasswordDto;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidPasswordException;
import org.mjaworski.backend.exception.forbidden.WrongOldPasswordException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.service.EmailService;
import org.mjaworski.backend.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class PasswordServiceImpl implements PasswordService {
    private UserRepository userRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private Environment environment;

    @Autowired
    public PasswordServiceImpl(UserRepository userRepository,
                               EmailService emailService,
                               PasswordEncoder passwordEncoder,
                               Environment environment) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    private int getRandomPasswordCharactersCount() {
        return Integer.parseInt(Objects.requireNonNull(environment.getProperty("config.password-reset.length")));
    }
    private String getCharacters() {
        return environment.getProperty("config.password-reset.characters");
    }
    @Override
    public void changePassword(int userId, PasswordChangeDto passwords, String token) throws UserNotFoundException, WrongOldPasswordException, InvalidPasswordException {
        User user = userRepository.getById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(passwords.getOldPassword(), user.getPassword())) {
            validatePassword(passwords.getNewPassword());
            user.setPassword(encode(passwords.getNewPassword()));
        } else {
            throw new WrongOldPasswordException();
        }
        userRepository.save(user);
    }
    @Override
    public void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < User.MIN_PASSWORD_LENGTH || password.length() > User.MAX_PASSWORD_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPassword) {
        userRepository.getByEmail(resetPassword.getEmail())
                .ifPresent(user -> {
            String newPassword = RandomStringUtils.random(getRandomPasswordCharactersCount(), getCharacters());
            user.setPassword(encode(newPassword));
            emailService.sendResetPasswordEmail(user.getEmail(),
                    user.getUsername(),
                    newPassword);
            userRepository.save(user);
        });
    }
}
