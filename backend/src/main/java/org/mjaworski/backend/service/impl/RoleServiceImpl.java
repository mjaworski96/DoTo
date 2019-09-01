package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.RoleConverter;
import org.mjaworski.backend.dto.role.RoleChangeDto;
import org.mjaworski.backend.dto.role.RolesChangesDto;
import org.mjaworski.backend.dto.role.RolesDto;
import org.mjaworski.backend.exception.not_found.RoleNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.Role;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.RoleRepository;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role getRole(String name) throws RoleNotFoundException {
        Role role = roleRepository.get(name)
                .orElseThrow(RoleNotFoundException::new);
        return role;
    }
    @Override
    public RolesDto getUserRoles(String username) throws UserNotFoundException {
        User user = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return RoleConverter.getRolesDto(user.getRoles());
    }
    @Override
    public List<Role> getRoles(String... roles) throws RoleNotFoundException {
        List<Role> rolesFromDb = new ArrayList<>(roles.length);

        for (int i = 0; i < roles.length; i++) {
            rolesFromDb.add(getRole(roles[i]));
        }
        return rolesFromDb;
    }
    private boolean hasRole(User user, String role) throws RoleNotFoundException {
        return user.getRoles().contains(getRoles(role).get(0));
    }
    private void addRole(User user, String role) throws RoleNotFoundException {
        user.getRoles().add(
                getRoles(role).get(0)
        );
    }
    private void removeRole(User user, String role) throws RoleNotFoundException {
        user.getRoles().remove(getRoles(role).get(0));
    }
    @Override
    public RolesDto updateRoles(String username, RolesChangesDto roles) throws UserNotFoundException, RoleNotFoundException {
        User user = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        for (int i = 0; i < roles.getRoles().size(); i++) {
            RoleChangeDto role = roles.getRoles().get(i);
            if (hasRole(user, role.getRoleName())) {
                if (role.getOperation().equals("remove")) {
                    removeRole(user, role.getRoleName());
                }
            } else {
                if (role.getOperation().equals("add")) {
                    addRole(user, role.getRoleName());
                }
            }
        }
        userRepository.save(user);
        return RoleConverter.getRolesDto(user.getRoles());
    }
}
