package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserLoginResponseDto;
import org.mjaworski.backend.dto.user.UserRegisterDetailsDto;
import org.mjaworski.backend.dto.user.UserUpdateDataDto;
import org.mjaworski.backend.exception.LocalizedException;
import org.mjaworski.backend.exception.bad_request.InvalidEmailException;
import org.mjaworski.backend.exception.bad_request.InvalidPasswordException;
import org.mjaworski.backend.exception.bad_request.InvalidUsernameException;
import org.mjaworski.backend.exception.conflict.EmailNotUniqueException;
import org.mjaworski.backend.exception.conflict.UsernameNotUniqueException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.RoleNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface UserService {
    void canPerformOperation(String username, String authorizationToken) throws ForbiddenException;
    UserLoginResponseDto addUser(UserRegisterDetailsDto userRegisterData, String... roles) throws RoleNotFoundException, UsernameNotUniqueException, InvalidUsernameException, InvalidPasswordException, EmailNotUniqueException, InvalidEmailException;
    UserLoginResponseDto updateUser(String username, UserUpdateDataDto userRegisterDetails, String authorizationToken) throws LocalizedException, ForbiddenException, UserNotFoundException;
    void deleteUser(String username, String authorizationToken) throws ForbiddenException;
    UserDto getUser(String username, String authorizationToken) throws UserNotFoundException;
    Page<UserDto> getUsers(int page, int pageSize);
}
