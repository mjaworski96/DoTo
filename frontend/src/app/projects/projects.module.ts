import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsComponent } from './projects/projects.component';
import {ProjectsRoutingModule} from './projects-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProjectComponent } from './projects/project/project.component';
import { TasksListComponent } from './projects/project/tasks-list/tasks-list.component';
import { AddProjectComponent } from './projects/add-project/add-project.component';
import {ReactiveFormsModule} from '@angular/forms';
import { AddTaskComponent } from './projects/project/add-task/add-task.component';
import {DeleteProjectDialogComponent} from '../dialogs/delete-project-dialog/delete-project-dialog.component';
import { TaskComponent } from './projects/project/tasks-list/task/task.component';
import { CommentsListComponent } from './projects/project/tasks-list/task/comments-list/comments-list.component';
import {DeleteTaskDialogComponent} from '../dialogs/delete-task-dialog/delete-task-dialog.component';
import { AddCommentComponent } from './projects/project/tasks-list/task/comments-list/add-comment/add-comment.component';
import {ModifyProjectDialogComponent} from '../dialogs/modify-project-dialog/modify-project-dialog.component';
import {ModifyTaskDialogComponent} from '../dialogs/modify-task-dialog/modify-task-dialog.component';
import {ModifyCommentDialogComponent} from '../dialogs/modify-comment-dialog/modify-comment-dialog.component';



@NgModule({
  declarations: [
    ProjectsComponent,
    ProjectComponent,
    TasksListComponent,
    AddProjectComponent,
    AddTaskComponent,
    TaskComponent,
    CommentsListComponent,
    AddCommentComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgbModule,
    ReactiveFormsModule
  ],
  exports: [
    AddProjectComponent,
    AddTaskComponent,
    AddCommentComponent
  ],
  entryComponents: [
    DeleteProjectDialogComponent,
    TaskComponent,
    DeleteTaskDialogComponent,
    ModifyProjectDialogComponent,
    ModifyTaskDialogComponent,
    ModifyCommentDialogComponent
  ]
})
export class ProjectsModule { }
