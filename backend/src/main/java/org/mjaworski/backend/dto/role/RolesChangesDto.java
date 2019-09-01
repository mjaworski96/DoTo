package org.mjaworski.backend.dto.role;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RolesChangesDto {
    private List<RoleChangeDto> roles;
}