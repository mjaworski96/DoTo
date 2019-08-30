package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.user.UserLoginDetailsDto;
import org.mjaworski.backend.persistance.entity.User;

public abstract class UserConverter extends ConverterBase {
    public static UserLoginDetailsDto getUserLoginDetails(User user) {
        UserLoginDetailsDto loginDetails = mapper.map(user, UserLoginDetailsDto.class);
        loginDetails.setRoles(RoleConverter.getRolesDto(
                user.getRoles()).getRoles()
        );

        return loginDetails;
    }
}
