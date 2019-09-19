import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsComponent } from './projects/projects.component';
import {ProjectsRoutingModule} from './projects-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProjectComponent } from './projects/project/project.component';
import { TasksListComponent } from './projects/project/tasks-list/tasks-list.component';



@NgModule({
  declarations: [ProjectsComponent, ProjectComponent, TasksListComponent],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgbModule
  ]
})
export class ProjectsModule { }
