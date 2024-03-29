package org.mjaworski.backend.dto.user;

import lombok.*;
import org.mjaworski.backend.dto.role.RoleDto;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDto {
    private int id;
    private String username;
    private String email;
    private List<RoleDto> roles;
}
