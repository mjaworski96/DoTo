package org.mjaworski.backend.dto.role;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RoleDto {
    private String name;
}
