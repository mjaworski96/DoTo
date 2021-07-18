package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.label.LabelDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.dto.label.LabelDtoWithIdList;
import org.mjaworski.backend.persistance.entity.Label;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LabelConverter extends BaseConverter {
    public static LabelDtoWithId getLabelDtoWithId(Label label) {
        return mapper.map(label, LabelDtoWithId.class);
    }
    public static LabelDtoWithIdList getLabelDtoWithIdList(Collection<Label> labels) {
        List<LabelDtoWithId> result = new ArrayList<>(labels.size());

        labels.forEach(item -> result.add(getLabelDtoWithId(item)));

        return new LabelDtoWithIdList(result);
    }
    public static Label getLabel(LabelDto labelDto) {
        return mapper.map(labelDto, Label.class);
    }
    public static void rewrite(Label destination, LabelDto source) {
        mapper.map(source, destination);
    }

}
