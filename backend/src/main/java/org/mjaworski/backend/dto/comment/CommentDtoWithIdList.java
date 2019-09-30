package org.mjaworski.backend.dto.comment;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentDtoWithIdList {
    private List<CommentDtoWithId> comments;
}
