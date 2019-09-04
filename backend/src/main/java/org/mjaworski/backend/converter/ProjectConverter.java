package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.persistance.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectConverter extends BaseConverter {
    public static ProjectDtoWithId getProjectDtoWithId(Project project) {
        return mapper.map(project, ProjectDtoWithId.class);
    }
    public static List<ProjectDtoWithId> getProjectDtoWithIdList(List<Project> project) {
        List<ProjectDtoWithId> result = new ArrayList<>(project.size());

        project.forEach(item -> result.add(getProjectDtoWithId(item)));

        return result;
    }
    public static Project getProject(ProjectDto projectDto) {
        return mapper.map(projectDto, Project.class);
    }
    public static void rewrite(Project destination, ProjectDto source) {
        mapper.map(source, destination);
    }
}
