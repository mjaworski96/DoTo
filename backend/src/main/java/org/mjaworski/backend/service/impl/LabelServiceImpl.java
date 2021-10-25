package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.LabelConverter;
import org.mjaworski.backend.dto.IdDto;
import org.mjaworski.backend.dto.label.LabelDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.dto.label.LabelDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.label.InvalidLabelNameException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.LabelNotFoundException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.persistance.entity.Label;
import org.mjaworski.backend.persistance.entity.Project;
import org.mjaworski.backend.persistance.entity.Task;
import org.mjaworski.backend.persistance.repository.LabelRepository;
import org.mjaworski.backend.persistance.repository.ProjectRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {
    private static Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);

    private LabelRepository labelRepository;
    private ProjectRepository projectRepository;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository, ProjectRepository projectRepository, TokenAuthentication tokenAuthentication) {
        this.labelRepository = labelRepository;
        this.projectRepository = projectRepository;
        this.tokenAuthentication = tokenAuthentication;
    }

    @Override
    public LabelDtoWithId create(int projectId, LabelDto label, String token) throws ProjectNotFoundException, ForbiddenException, InvalidLabelNameException {
        validateLabel(label);
        Project project = getProject(projectId);
        checkOwner(project, token);
        Label labelEntity = LabelConverter.getLabel(label);
        labelEntity.setProject(project);
        labelRepository.save(labelEntity);
        return LabelConverter.getLabelDtoWithId(labelEntity);
    }

    @Override
    public LabelDtoWithIdList getAll(int projectId, String token) {
        List<Label> labels = labelRepository.getAll(projectId);
        return LabelConverter.getLabelDtoWithIdList(labels);
    }

    @Override
    public LabelDtoWithId update(int id, LabelDto label, String token) throws LabelNotFoundException, InvalidLabelNameException {
        validateLabel(label);
        Label labelEntity = labelRepository.get(id)
                .orElseThrow(LabelNotFoundException::new);
        LabelConverter.rewrite(labelEntity, label);
        labelRepository.save(labelEntity);
        return LabelConverter.getLabelDtoWithId(labelEntity);
    }

    @Override
    public void delete(int id, String token) {
        try {
            Optional<Label> label = labelRepository.get(id);
            if (label.isPresent()) {
                checkOwner(label.get(), token);
                labelRepository.delete(label.get());
            }
        } catch (EmptyResultDataAccessException | ForbiddenException e) {
            logger.warn("Label ({}) already deleted", id);
        }
    }

    @Override
    public List<Label> getAll(List<IdDto> ids) throws LabelNotFoundException {
        if (ids == null) {
            return new ArrayList<>();
        }
        List<Label> result = new ArrayList<>(ids.size());
        for (IdDto id : ids) {
            result.add(labelRepository.get(id.getId())
                .orElseThrow(LabelNotFoundException::new));
        }

        return result;
    }

    private Project getProject(int id) throws ProjectNotFoundException {
        return projectRepository.get(id)
                .orElseThrow(ProjectNotFoundException::new);
    }
    private void checkOwner(Project project, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(project.getOwner().getId(), token))
            throw new ForbiddenException();
    }
    private void checkOwner(Label label, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(label.getProject().getOwner().getId(), token))
            throw new ForbiddenException();
    }
    private void validateLabel(LabelDto label) throws InvalidLabelNameException {
        if (label.getName().length() > Label.MAX_NAME_LENGTH ||
            label.getName().length() == 0) {
            throw new InvalidLabelNameException();
        }
    }
}
