package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.task.StateDto;
import org.mjaworski.backend.dto.task.TaskDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.dto.task.TaskDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidTaskException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.StateNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;

public interface TaskService {
    TaskDtoWithId getOne(int id, String token) throws TaskNotFoundException, ForbiddenException;
    TaskDtoWithIdList getAll(int projectId, String token) throws ForbiddenException, ProjectNotFoundException;
    TaskDtoWithId add(int projectId, TaskDto taskDto, String token) throws ProjectNotFoundException, ForbiddenException, InvalidTaskException;
    TaskDtoWithId modify(int taskId, TaskDto taskDto, String token) throws TaskNotFoundException, ForbiddenException, ProjectNotFoundException, InvalidTaskException;
    StateDto updateState(int taskId, StateDto stateDto, String token) throws TaskNotFoundException, StateNotFoundException, ForbiddenException;
    void delete(int taskId, String token) throws ForbiddenException;
}
