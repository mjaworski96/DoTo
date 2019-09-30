package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.task.TaskDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.persistance.entity.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TaskConverter extends BaseConverter {
    public static TaskDtoWithId getTaskDtoWithId(Task task) {
        return mapper.map(task, TaskDtoWithId.class);
    }
    public static List<TaskDtoWithId> getTaskDtoWithIdList(Collection<Task> tasks) {
        List<TaskDtoWithId> result = new ArrayList<>(tasks.size());

        tasks.forEach(item -> result.add(getTaskDtoWithId(item)));

        return result;
    }
    public static Task getTask(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }
    public static void rewrite(Task destination, TaskDto source) {
        mapper.map(source, destination);
    }
}
