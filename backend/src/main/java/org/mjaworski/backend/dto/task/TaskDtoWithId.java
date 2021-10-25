package org.mjaworski.backend.dto.task;

import lombok.*;
import org.mjaworski.backend.dto.IdDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskDtoWithId {
    private int id;
    private String shortDescription;
    private String fullDescription;
    private StateDto state;
    private IdDto project;
    private List<LabelDtoWithId> labels;
}
