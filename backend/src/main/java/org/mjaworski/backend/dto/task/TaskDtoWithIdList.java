package org.mjaworski.backend.dto.task;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskDtoWithIdList {
    private List<TaskDtoWithId> tasks;
}
