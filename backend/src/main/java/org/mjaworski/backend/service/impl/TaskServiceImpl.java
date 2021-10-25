package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.TaskConverter;
import org.mjaworski.backend.dto.task.*;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidTaskFullDescriptionException;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidProjectShortDescriptionException;
import org.mjaworski.backend.exception.bad_request.invalid.task.InvalidTaskException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.LabelNotFoundException;
import org.mjaworski.backend.exception.not_found.ProjectNotFoundException;
import org.mjaworski.backend.exception.not_found.StateNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;
import org.mjaworski.backend.persistance.entity.Label;
import org.mjaworski.backend.persistance.entity.Project;
import org.mjaworski.backend.persistance.entity.State;
import org.mjaworski.backend.persistance.entity.Task;
import org.mjaworski.backend.persistance.repository.ProjectRepository;
import org.mjaworski.backend.persistance.repository.StateRepository;
import org.mjaworski.backend.persistance.repository.TaskRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.LabelService;
import org.mjaworski.backend.service.TaskService;
import org.mjaworski.backend.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private StateRepository stateRepository;
    private LabelService labelService;
    private TokenAuthentication tokenAuthentication;
    private String newTaskDefaultState;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           ProjectRepository projectRepository,
                           StateRepository stateRepository,
                           LabelService labelService,
                           TokenAuthentication tokenAuthentication,
                           Environment environment) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.stateRepository = stateRepository;
        this.labelService = labelService;
        this.tokenAuthentication = tokenAuthentication;
        this.newTaskDefaultState = environment.getProperty("config.task.default_state");
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
    public ActiveTaskDtoList getAllToDo(int userId, String token) throws ForbiddenException {
        checkOwner(userId, token);
        List<Task> toDoTasks = taskRepository.getAllToDo(userId);
        List<Task> inProgressTasks = taskRepository.getAllInProgress(userId);

        return ActiveTaskDtoList.builder()
                .toDo(TaskConverter.getToDoTaskDtoList(inProgressTasks))
                .inProgress(TaskConverter.getToDoTaskDtoList(toDoTasks))
                .build();
    }

    @Override
    public TaskDtoWithId add(int projectId, TaskDto taskDto, String token) throws ProjectNotFoundException, ForbiddenException, InvalidTaskException, LabelNotFoundException {
        validate(taskDto);
        Project project = getProject(projectId);
        checkOwner(project, token);
        Task task = TaskConverter.getTask(taskDto);
        task.setProject(project);
        task.setState(stateRepository.get(newTaskDefaultState).get());
        List<Label> labels = labelService.getAll(taskDto.getLabels());
        task.setLabels(new HashSet<>(labels));
        taskRepository.save(task);
        return TaskConverter.getTaskDtoWithId(task);
    }

    @Override
    public TaskDtoWithId modify(int taskId, TaskDto taskDto, String token) throws TaskNotFoundException, ForbiddenException, InvalidTaskException, LabelNotFoundException {
        validate(taskDto);
        Task task = getTask(taskId);
        checkOwner(task, token);
        TaskConverter.rewrite(task, taskDto);
        List<Label> labels = labelService.getAll(taskDto.getLabels());
        task.setLabels(new HashSet<>(labels));
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
        checkOwner(project.getOwner().getId(), token);
    }

    private void checkOwner(Task task, String token) throws ForbiddenException {
        checkOwner(task.getProject(), token);
    }
    private void checkOwner(int userId, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(userId, token))
            throw new ForbiddenException();
    }
    private void validate(TaskDto taskDto) throws InvalidProjectShortDescriptionException, InvalidTaskFullDescriptionException {
        validateShortDescription(taskDto);
        validateFullDescription(taskDto);
    }

    private void validateFullDescription(TaskDto taskDto) throws InvalidTaskFullDescriptionException {
        if (!ValidationUtils.isValid(taskDto.getFullDescription(),
                Task.MAX_FULL_DESCRIPTION_LENGTH))
            throw new InvalidTaskFullDescriptionException();
    }

    private void validateShortDescription(TaskDto taskDto) throws InvalidProjectShortDescriptionException {
        if(!ValidationUtils.isValid(taskDto.getShortDescription(),
                Task.MIN_SHORT_DESCRIPTION_LENGTH,
                Task.MAX_SHORT_DESCRIPTION_LENGTH))
            throw new InvalidProjectShortDescriptionException();
    }
}
