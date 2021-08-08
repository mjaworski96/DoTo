package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.task.*;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidTaskException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.LabelNotFoundException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.StateNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;

public interface TaskService {
    TaskDtoWithId getOne(int id, String token) throws TaskNotFoundException, ForbiddenException;
    TaskDtoWithIdList getAll(int projectId, String token) throws ForbiddenException, ProjectNotFoundException;
    ActiveTaskDtoList getAllToDo(int userId, String token) throws ForbiddenException;
    TaskDtoWithId add(int projectId, TaskDto taskDto, String token) throws ProjectNotFoundException, ForbiddenException, InvalidTaskException, LabelNotFoundException;
    TaskDtoWithId modify(int taskId, TaskDto taskDto, String token) throws TaskNotFoundException, ForbiddenException, InvalidTaskException, LabelNotFoundException;
    StateDto updateState(int taskId, StateDto stateDto, String token) throws TaskNotFoundException, StateNotFoundException, ForbiddenException;
    void delete(int taskId, String token) throws ForbiddenException;
}
