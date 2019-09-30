package org.mjaworski.backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ApiError {
    private int code;
    private String message;
}
