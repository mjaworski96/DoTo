package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.TaskConverter;
import org.mjaworski.backend.dto.task.StateDto;
import org.mjaworski.backend.dto.task.TaskDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.dto.task.TaskDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidFullDescriptionException;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidShortDescriptionException;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidTaskException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.StateNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;
import org.mjaworski.backend.persistance.entity.Project;
import org.mjaworski.backend.persistance.entity.State;
import org.mjaworski.backend.persistance.entity.Task;
import org.mjaworski.backend.persistance.repository.ProjectRepository;
import org.mjaworski.backend.persistance.repository.StateRepository;
import org.mjaworski.backend.persistance.repository.TaskRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.TaskService;
import org.mjaworski.backend.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private StateRepository stateRepository;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, StateRepository stateRepository, TokenAuthentication tokenAuthentication) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.stateRepository = stateRepository;
        this.tokenAuthentication = tokenAuthentication;
    }

    @Override
    public TaskDtoWithId getOne(int id, String token) throws TaskNotFoundException, ForbiddenException {
        Task task = getTask(id);
        checkOwner(task, token);
        return TaskConverter.getTaskDtoWithId(task);
    }

    @Override
    public TaskDtoWithIdList getAll(int projectId, String token) throws ForbiddenException, ProjectNotFoundException {
        Project project = getProject(projectId);
        checkOwner(project, token);
        List<TaskDtoWithId> tasks = TaskConverter.getTaskDtoWithIdList(project.getTasks());
        return TaskDtoWithIdList.builder()
                .tasks(tasks)
                .build();
    }

    @Override
    public TaskDtoWithId add(int projectId, TaskDto taskDto, String token) throws ProjectNotFoundException, ForbiddenException, InvalidTaskException {
        validate(taskDto);
        Project project = getProject(projectId);
        checkOwner(project, token);
        Task task = TaskConverter.getTask(taskDto);
        task.setProject(project);
        task.setState(stateRepository.get("todo").get());
        taskRepository.save(task);
        return TaskConverter.getTaskDtoWithId(task);
    }

    @Override
    public TaskDtoWithId modify(int taskId, TaskDto taskDto, String token) throws TaskNotFoundException, ForbiddenException, ProjectNotFoundException, InvalidTaskException {
        validate(taskDto);
        Task task = getTask(taskId);
        checkOwner(task, token);
        TaskConverter.rewrite(task, taskDto);
        taskRepository.save(task);
        return TaskConverter.getTaskDtoWithId(task);
    }

    @Override
    public StateDto updateState(int taskId, StateDto stateDto, String token) throws TaskNotFoundException, StateNotFoundException, ForbiddenException {
        Task task = getTask(taskId);
        checkOwner(task, token);
        State newState = getState(stateDto.getName());
        task.setState(newState);
        taskRepository.save(task);
        return StateDto.builder()
                .name(task.getState().getName())
                .build();
    }

    @Override
    public void delete(int taskId, String token) throws ForbiddenException {
        try {
            Optional<Task> task = taskRepository.get(taskId);
            if (task.isPresent()) {
                checkOwner(task.get(), token);
                taskRepository.delete(task.get());
            }
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Task ({}) already deleted", taskId);
        }
    }

    private Project getProject(int id) throws ProjectNotFoundException {
        return projectRepository.get(id)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private Task getTask(int id) throws TaskNotFoundException {
        return taskRepository.get(id)
                .orElseThrow(TaskNotFoundException::new);
    }
    private State getState(String name) throws StateNotFoundException {
        return stateRepository.get(name)
                .orElseThrow(StateNotFoundException::new);
    }

    private void checkOwner(Project project, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(project.getOwner().getUsername(), token))
            throw new ForbiddenException();
    }

    private void checkOwner(Task task, String token) throws ForbiddenException {
        checkOwner(task.getProject(), token);
    }

    private void validate(TaskDto taskDto) throws InvalidShortDescriptionException, InvalidFullDescriptionException {
        validateShortDescription(taskDto);
        validateFullDescription(taskDto);
    }

    private void validateFullDescription(TaskDto taskDto) throws InvalidFullDescriptionException {
        if (!ValidationUtils.isValid(taskDto.getFullDescription(),
                Task.MAX_FULL_DESCRIPTION_LENGTH))
            throw new InvalidFullDescriptionException();
    }

    private void validateShortDescription(TaskDto taskDto) throws InvalidShortDescriptionException {
        if(!ValidationUtils.isValid(taskDto.getShortDescription(),
                Task.MIN_SHORT_DESCRIPTION_LENGTH,
                Task.MAX_SHORT_DESCRIPTION_LENGTH))
            throw new InvalidShortDescriptionException();
    }
}
