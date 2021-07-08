package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.UserConverter;
import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserRegisterDetailsDto;
import org.mjaworski.backend.dto.user.UserUpdateDataDto;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidEmailException;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidPasswordException;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidUserException;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidUsernameException;
import org.mjaworski.backend.exception.conflict.not_unique.DataNotUniqueException;
import org.mjaworski.backend.exception.conflict.not_unique.EmailNotUniqueException;
import org.mjaworski.backend.exception.conflict.not_unique.UsernameNotUniqueException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.RoleNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.Role;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.EmailService;
import org.mjaworski.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private RoleServiceImpl roleService;
    private TokenAuthentication tokenAuthentication;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, TokenAuthentication tokenAuthentication, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.tokenAuthentication = tokenAuthentication;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void canPerformOperation(int userId, String authorizationToken) throws ForbiddenException {
        if (!tokenAuthentication.checkUser(userId, authorizationToken)) {
            throw new ForbiddenException();
        }
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDto addUser(UserRegisterDetailsDto userRegisterData, String... roles) throws RoleNotFoundException, InvalidUserException, DataNotUniqueException {
        validate(userRegisterData);

        List<Role> rolesFromDb = roleService.getRoles(roles);
        User user = UserConverter.getUser(userRegisterData);

        user.setPassword(encodePassword(userRegisterData.getPassword()));
        user.setRoles(new HashSet<>(rolesFromDb));

        userRepository.save(user);
        emailService.sendRegisterEmail(user.getEmail(), user.getUsername());
        return UserConverter.getUserDto(user);
    }
    @Override
    public UserDto updateUser(int userId, UserUpdateDataDto userUpdateData, String authorizationToken) throws UserNotFoundException, ForbiddenException, InvalidUserException, DataNotUniqueException {
        canPerformOperation(userId, authorizationToken);
        User currentUser = userRepository.getById(userId)
                .orElseThrow(UserNotFoundException::new);

        validate(userUpdateData, currentUser);

        UserConverter.rewrite(currentUser, userUpdateData);

        userRepository.save(currentUser);
        return UserConverter.getUserDto(currentUser);
    }
    @Override
    public void deleteUser(int userId, String authorizationToken) throws ForbiddenException {
        canPerformOperation(userId, authorizationToken);
        Optional<User> userOptional = userRepository.getById(userId);
        if (userOptional.isPresent()) {
            try {
                userRepository.delete(userOptional.get());
            } catch (EmptyResultDataAccessException e) {
                logger.warn("User already deleted", e);
            }
        }
    }
    @Override
    public UserDto getUser(int userId, String authorizationToken) throws UserNotFoundException, ForbiddenException {
        User user = userRepository.getById(userId).orElseThrow(UserNotFoundException::new);
        isOwnerOrAdmin(userId, authorizationToken);
        return UserConverter.getUserDto(user);
    }

    @Override
    public Page<UserDto> getUsers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<User> users = userRepository.get(pageable);
        return new PageImpl<>(UserConverter.getUserDtoList(users),
                pageable,
                userRepository.getCount());
    }

    private void isOwnerOrAdmin(int userId, String authorizationToken) throws ForbiddenException {
        if (!tokenAuthentication.checkUser(userId, authorizationToken) &&
            !tokenAuthentication.isAdmin(authorizationToken)) {
            throw new ForbiddenException();
        }
    }

    private void validate(UserRegisterDetailsDto userRegisterData) throws InvalidPasswordException, UsernameNotUniqueException, InvalidUsernameException, EmailNotUniqueException, InvalidEmailException {
        validateUsername(userRegisterData.getUsername());
        validatePassword(userRegisterData.getPassword());
        validateEmail(userRegisterData.getEmail());
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < User.MIN_USERNAME_LENGTH || password.length() > User.MAX_USERNAME_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    private void validate(UserUpdateDataDto userUpdateData, User currentUser) throws UsernameNotUniqueException, InvalidUsernameException, EmailNotUniqueException, InvalidEmailException {
        validateUsername(userUpdateData.getUsername(), currentUser.getUsername());
        validateEmail(currentUser.getId(), userUpdateData.getEmail());
    }

    private void validateUsernameLength(String username) throws InvalidUsernameException {
        if (username.length() < User.MIN_USERNAME_LENGTH || username.length() > User.MAX_USERNAME_LENGTH) {
            throw new InvalidUsernameException();
        }
    }
    private void validateUsername(String username) throws InvalidUsernameException, UsernameNotUniqueException {
        validateUsernameLength(username);

        if (userRepository.getByUsername(username).isPresent()) {
            throw new UsernameNotUniqueException();
        }
    }
    private void validateUsername(String username, String currentUsername) throws InvalidUsernameException, UsernameNotUniqueException {
        validateUsernameLength(username);

        if (!username.equals(currentUsername) && userRepository.getByUsername(username).isPresent()) {
            throw new UsernameNotUniqueException();
        }
    }
    private void validateEmail(String email) throws EmailNotUniqueException, InvalidEmailException {
        emailService.validateEmail(email);
        emailService.checkIfEmailIsUnique(-1, email);
    }
    private void validateEmail(int userId, String email) throws EmailNotUniqueException, InvalidEmailException {
        emailService.validateEmail(email);
        emailService.checkIfEmailIsUnique(userId, email);
    }
}
