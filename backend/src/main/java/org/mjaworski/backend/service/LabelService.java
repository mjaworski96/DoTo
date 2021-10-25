package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.IdDto;
import org.mjaworski.backend.dto.label.LabelDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.dto.label.LabelDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.label.InvalidLabelNameException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.LabelNotFoundException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.persistance.entity.Label;

import java.util.List;

public interface LabelService {
    LabelDtoWithId create(int projectId, LabelDto label, String token) throws ProjectNotFoundException, ForbiddenException, InvalidLabelNameException;
    LabelDtoWithIdList getAll(int projectId, String token);
    LabelDtoWithId update(int id, LabelDto label, String token) throws LabelNotFoundException, InvalidLabelNameException;
    void delete(int id, String token);
    List<Label> getAll(List<IdDto> ids) throws LabelNotFoundException;
}
