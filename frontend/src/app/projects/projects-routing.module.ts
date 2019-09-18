import {Route, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoggedUserGuard} from '../guards/logged-user-guard';
import {ProjectsComponent} from './projects/projects.component';
import {ProjectResolveService} from './services/project-resolve.service';

const PROJECTS_ROUTES: Route[] = [
  {
    path: 'projects',
    component: ProjectsComponent as any,
    resolve: {
      projects: ProjectResolveService
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
