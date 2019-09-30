package org.mjaworski.backend.dto.task;

import lombok.*;
import org.mjaworski.backend.dto.IdDto;

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
}
