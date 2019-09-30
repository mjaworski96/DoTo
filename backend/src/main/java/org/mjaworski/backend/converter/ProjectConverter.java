package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.persistance.entity.Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ProjectConverter extends BaseConverter {
    public static ProjectDtoWithId getProjectDtoWithId(Project project) {
        return mapper.map(project, ProjectDtoWithId.class);
    }
    public static List<ProjectDtoWithId> getProjectDtoWithIdList(Collection<Project> projects) {
        List<ProjectDtoWithId> result = new ArrayList<>(projects.size());

        projects.forEach(item -> result.add(getProjectDtoWithId(item)));

        return result;
    }
    public static Project getProject(ProjectDto projectDto) {
        return mapper.map(projectDto, Project.class);
    }
    public static void rewrite(Project destination, ProjectDto source) {
        mapper.map(source, destination);
    }
}
