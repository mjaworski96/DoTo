package org.mjaworski.backend.dto.task;

import lombok.*;
import org.mjaworski.backend.dto.IdDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskDto {
    private String shortDescription;
    private String fullDescription;
    private List<IdDto> labels;
}
