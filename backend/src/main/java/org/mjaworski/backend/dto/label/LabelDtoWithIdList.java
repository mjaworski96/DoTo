package org.mjaworski.backend.dto.label;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LabelDtoWithIdList {
    private List<LabelDtoWithId> labels;
}
