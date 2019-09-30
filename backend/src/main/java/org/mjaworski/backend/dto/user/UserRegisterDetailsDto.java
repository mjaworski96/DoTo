package org.mjaworski.backend.dto.user;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRegisterDetailsDto {
    private String username;
    private String password;
    private String email;
}

