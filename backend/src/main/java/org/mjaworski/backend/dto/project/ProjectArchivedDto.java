package org.mjaworski.backend.dto.project;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProjectArchivedDto {
    private boolean archived;
}
