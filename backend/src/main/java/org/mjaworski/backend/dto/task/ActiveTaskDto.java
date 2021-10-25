package org.mjaworski.backend.dto.task;

import lombok.*;
import org.mjaworski.backend.dto.IdDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ActiveTaskDto {
    private int id;
    private String shortDescription;
    private String fullDescription;
    private ProjectDtoWithId project;
    private List<LabelDtoWithId> labels;
}
