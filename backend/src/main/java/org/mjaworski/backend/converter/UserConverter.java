package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserLoginResponseDto;
import org.mjaworski.backend.dto.user.UserRegisterDetailsDto;
import org.mjaworski.backend.dto.user.UserUpdateDataDto;
import org.mjaworski.backend.persistance.entity.User;

import java.util.ArrayList;
import java.util.List;

public abstract class UserConverter extends BaseConverter {
    public static UserLoginResponseDto getUserLoginDetails(User user) {
        UserLoginResponseDto loginDetails = mapper.map(user, UserLoginResponseDto.class);
        loginDetails.setRoles(RoleConverter.getRolesDto(
                user.getRoles()).getRoles()
        );

        return loginDetails;
    }

    public static User getUser(UserRegisterDetailsDto userRegisterData) {
        return mapper.map(userRegisterData, User.class);
    }

    public static void rewrite(User desc, UserUpdateDataDto src) {
        mapper.map(src, desc);
    }

    public static UserDto getUserDto(User user, boolean includeEmail) {
        UserDto userData = mapper.map(user, UserDto.class);

        if (!includeEmail) {
            userData.setEmail(null);
        }

        userData.setRoles(RoleConverter.getRolesDto(
                user.getRoles()).getRoles()
        );
        return userData;
    }

    public static List<UserDto> getUserDtoList(List<User> users, boolean includeEmail) {
        List<UserDto> userDtoList = new ArrayList<>(users.size());

        users.forEach(item ->
                userDtoList.add(getUserDto(item, includeEmail)));

        return userDtoList;
    }
}
