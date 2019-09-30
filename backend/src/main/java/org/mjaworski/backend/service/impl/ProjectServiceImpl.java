package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.ProjectConverter;
import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.exception.bad_request.invalid.project.InvalidProjectDescriptionException;
import org.mjaworski.backend.exception.bad_request.invalid.project.InvalidProjectNameException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.Project;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.ProjectRepository;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.ProjectService;
import org.mjaworski.backend.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, TokenAuthentication tokenAuthentication) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tokenAuthentication = tokenAuthentication;
    }

    @Override
    public ProjectDtoWithId getOne(int id, String token) throws ProjectNotFoundException, ForbiddenException {
        Project project = getProject(id);
        checkUser(project, token);
        return ProjectConverter.getProjectDtoWithId(project);
    }

    @Override
    public Page<ProjectDtoWithId> getForUser(String username, Pageable pageable, String token) throws ForbiddenException, UserNotFoundException {
        checkUser(username, token);
        getUser(username);
        List<Project> projects = projectRepository.get(username, pageable);
        List<ProjectDtoWithId> projectsDto = ProjectConverter.getProjectDtoWithIdList(projects);
        int count = projectRepository.getCount(username);
        return new PageImpl<>(projectsDto, pageable, count);
    }

    @Override
    public ProjectDtoWithId add(String username, ProjectDto projectDto, String token) throws UserNotFoundException, ForbiddenException, InvalidProjectNameException, InvalidProjectDescriptionException {
        checkUser(username, token);
        validate(projectDto);
        User user = getUser(username);
        Project project = ProjectConverter.getProject(projectDto);
        project.setOwner(user);
        projectRepository.save(project);
        return ProjectConverter.getProjectDtoWithId(project);
    }

    @Override
    public ProjectDtoWithId modify(int projectId, ProjectDto projectDto, String token) throws UserNotFoundException, ForbiddenException, ProjectNotFoundException, InvalidProjectNameException, InvalidProjectDescriptionException {
        validate(projectDto);
        Project project = getProject(projectId);
        checkUser(project.getOwner().getUsername(), token);
        ProjectConverter.rewrite(project, projectDto);
        projectRepository.save(project);
        return ProjectConverter.getProjectDtoWithId(project);
    }
    @Override
    public void delete(int projectId, String token) throws ForbiddenException {
        try {
            Optional<Project> project = projectRepository.get(projectId);
            if (project.isPresent()) {
               checkUser(project.get(), token);
               projectRepository.delete(project.get());
            }
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Project ({}) already deleted", projectId);
        }
    }

    private User getUser(String username) throws UserNotFoundException {
        return userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Project getProject(int projectId) throws ProjectNotFoundException {
        return projectRepository.get(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private void checkUser(String username, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(username, token))
            throw new ForbiddenException();
    }

    private void checkUser(Project project, String token) throws ForbiddenException {
        checkUser(project.getOwner().getUsername(), token);
    }

    private void validate(ProjectDto projectDto) throws InvalidProjectNameException, InvalidProjectDescriptionException {
        validateName(projectDto);
        validateDescription(projectDto);
    }

    private void validateDescription(ProjectDto projectDto) throws InvalidProjectDescriptionException {
        if (!ValidationUtils.isValid(projectDto.getDescription(),
                Project.MAX_DESCRIPTION_LENGTH))
            throw new InvalidProjectDescriptionException();
    }

    private void validateName(ProjectDto projectDto) throws InvalidProjectNameException {
        if(!ValidationUtils.isValid(projectDto.getName(),
                Project.MIN_NAME_LENGTH,
                Project.MAX_NAME_LENGTH))
            throw new InvalidProjectNameException();
    }

}
