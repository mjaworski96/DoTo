package org.mjaworski.backend.dto.task;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskDto {
    private String shortDescription;
    private String fullDescription;
}
