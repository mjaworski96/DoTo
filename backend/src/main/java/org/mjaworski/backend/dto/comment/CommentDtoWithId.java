package org.mjaworski.backend.dto.comment;

import lombok.*;
import org.mjaworski.backend.dto.IdDto;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentDtoWithId {
    private int id;
    private String content;
    private IdDto task;
}
