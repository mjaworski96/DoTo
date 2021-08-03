import {Route, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoggedUserGuard} from '../guards/logged-user-guard';
import {ProjectsComponent} from './projects/projects.component';
import {ArchivedProjectsResolveService, NotArchivedProjectsResolveService} from './services/projects-resolve.service';
import {ProjectComponent} from './projects/project/project.component';
import {ProjectResolveService} from './services/project-resolve.service';
import {TasksResolveService} from './services/tasks-resolve.service';
import { LabelsResolveService } from './services/labels-resolve.service';

const PROJECTS_ROUTES: Route[] = [
  {
    path: 'projects',
    component: ProjectsComponent as any,
    resolve: {
      projects: NotArchivedProjectsResolveService,
    },
    data: {
      archived: false
    },
    canActivate: [LoggedUserGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'archived-projects',
    component: ProjectsComponent as any,
    resolve: {
      projects: ArchivedProjectsResolveService
    },
    data: {
      archived: true
    },
    canActivate: [LoggedUserGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'projects/:projectId',
    component: ProjectComponent as any,
    resolve: {
      project: ProjectResolveService,
      tasks: TasksResolveService,
      labels: LabelsResolveService
    },
    canActivate: [LoggedUserGuard],
    runGuardsAndResolvers: 'always'
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(PROJECTS_ROUTES),
  ],
  exports: [
    RouterModule
  ]
})
export class ProjectsRoutingModule {}
