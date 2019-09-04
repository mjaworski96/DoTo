package org.mjaworski.backend.controller.swagger;

import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.springframework.data.domain.Page;

public abstract class ProjectsPage implements Page<ProjectDtoWithId> {
}
