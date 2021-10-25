package org.mjaworski.backend.dto.task;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ActiveTaskDtoList {
    private List<ActiveTaskDto> toDo;
    private List<ActiveTaskDto> inProgress;
}