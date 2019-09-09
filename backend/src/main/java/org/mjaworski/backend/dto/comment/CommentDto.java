package org.mjaworski.backend.dto.comment;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentDto {
    private String content;
}
