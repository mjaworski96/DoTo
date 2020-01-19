package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.dto.project.ProjectArchivedDto;
import org.mjaworski.backend.exception.bad_request.invalid.project.InvalidProjectException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectDtoWithId getOne(int id, String token) throws ProjectNotFoundException, ForbiddenException;
    Page<ProjectDtoWithId> getForUser(String username, boolean archived, Pageable pageable, String token) throws ForbiddenException, UserNotFoundException;
    ProjectDtoWithId add(String username, ProjectDto projectDto, String token) throws UserNotFoundException, ForbiddenException, InvalidProjectException;
    ProjectDtoWithId modify(int projectId, ProjectDto projectDto, String token) throws UserNotFoundException, ForbiddenException, ProjectNotFoundException, InvalidProjectException;
    void delete(int projectId, String token) throws ForbiddenException;
    ProjectArchivedDto modifyArchived(int projectId, ProjectArchivedDto archived, String token) throws UserNotFoundException, ForbiddenException, ProjectNotFoundException;
}
