package org.mjaworski.backend.service;


import org.mjaworski.backend.dto.role.RolesChangesDto;
import org.mjaworski.backend.dto.role.RolesDto;
import org.mjaworski.backend.exception.not_found.RoleNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.Role;
import java.util.List;

public interface RoleService {
    Role getRole(String name) throws RoleNotFoundException;
    List<Role> getRoles(String... roles) throws RoleNotFoundException;
    RolesDto getUserRoles(int userId) throws UserNotFoundException;
    RolesDto updateRoles(int userId, RolesChangesDto roles) throws UserNotFoundException, RoleNotFoundException;
}
