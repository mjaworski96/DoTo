import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsComponent } from './projects/projects.component';
import {ProjectsRoutingModule} from './projects-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProjectComponent } from './projects/project/project.component';
import { TasksListComponent } from './projects/project/tasks-list/tasks-list.component';
import { AddProjectComponent } from './projects/add-project/add-project.component';
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    ProjectsComponent,
    ProjectComponent,
    TasksListComponent,
    AddProjectComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgbModule,
    ReactiveFormsModule
  ]
})
export class ProjectsModule { }
