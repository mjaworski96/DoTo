package org.mjaworski.backend.dto.label;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LabelDtoWithId {
    private int id;
    private String name;
}
