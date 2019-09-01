package org.mjaworski.backend.dto.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserLoginCredentialsDto {
    private String username;
    private String password;
}