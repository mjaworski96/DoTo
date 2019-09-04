package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.role.RoleDto;
import org.mjaworski.backend.dto.role.RolesDto;
import org.mjaworski.backend.persistance.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RoleConverter extends BaseConverter {
    public static List<RoleDto> getRolesDtoList(Collection<Role> roles) {
        List<RoleDto> rolesDto = new ArrayList<>(roles.size());

        roles.forEach(role -> {
            rolesDto.add(getRoleDto(role));
        });

        return rolesDto;
    }
    public static RoleDto getRoleDto(Role role) {
        return mapper.map(role, RoleDto.class);
    }
    public static RolesDto getRolesDto(Collection<Role> roles) {
        return RolesDto.builder()
                .roles(getRolesDtoList(roles))
                .build();
    }
}