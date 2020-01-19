package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserRegisterDetailsDto;
import org.mjaworski.backend.dto.user.UserUpdateDataDto;
import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidUserException;
import org.mjaworski.backend.exception.conflict.not_unique.DataNotUniqueException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.RoleNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface UserService {
    void canPerformOperation(String username, String authorizationToken) throws ForbiddenException;
    UserDto addUser(UserRegisterDetailsDto userRegisterData, String... roles) throws RoleNotFoundException, InvalidUserException, DataNotUniqueException;
    UserDto updateUser(String username, UserUpdateDataDto userRegisterDetails, String authorizationToken) throws ForbiddenException, UserNotFoundException, InvalidUserException, DataNotUniqueException;
    void deleteUser(String username, String authorizationToken) throws ForbiddenException;
    UserDto getUser(String username, String authorizationToken) throws UserNotFoundException, ForbiddenException;
    Page<UserDto> getUsers(int page, int pageSize);
}
