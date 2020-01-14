package org.mjaworski.backend.dto.project;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProjectDtoWithId {
    private int id;
    private String name;
    private String description;
    private boolean archived;
}
