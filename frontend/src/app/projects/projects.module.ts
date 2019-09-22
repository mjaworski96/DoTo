import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsComponent } from './projects/projects.component';
import {ProjectsRoutingModule} from './projects-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProjectComponent } from './projects/project/project.component';
import { TasksListComponent } from './projects/project/tasks-list/tasks-list.component';
import { AddProjectComponent } from './projects/add-project/add-project.component';
import {ReactiveFormsModule} from "@angular/forms";
import { AddTaskComponent } from './projects/project/add-task/add-task.component';
import {DeleteProjectDialogComponent} from "../dialogs/delete-project-dialog/delete-project-dialog.component";



@NgModule({
  declarations: [
    ProjectsComponent,
    ProjectComponent,
    TasksListComponent,
    AddProjectComponent,
    AddTaskComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgbModule,
    ReactiveFormsModule
  ],
  entryComponents: [
    DeleteProjectDialogComponent
  ]
})
export class ProjectsModule { }
