import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsComponent } from './projects/projects.component';
import {ProjectsRoutingModule} from './projects-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ProjectComponent } from './projects/project/project.component';
import { TasksListComponent } from './projects/project/tasks-list/tasks-list.component';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import {ReactiveFormsModule} from '@angular/forms';
import { EditTaskComponent } from './projects/project/edit-task/edit-task.component';
import {DeleteProjectDialogComponent} from '../dialogs/delete-project-dialog/delete-project-dialog.component';
import { TaskComponent } from './projects/project/tasks-list/task/task.component';
import { CommentsListComponent } from './projects/project/tasks-list/task/comments-list/comments-list.component';
import {DeleteTaskDialogComponent} from '../dialogs/delete-task-dialog/delete-task-dialog.component';
import { EditCommentComponent } from './projects/project/tasks-list/task/comments-list/edit-comment/edit-comment.component';
import {ModifyProjectDialogComponent} from '../dialogs/modify-project-dialog/modify-project-dialog.component';
import {ModifyTaskDialogComponent} from '../dialogs/modify-task-dialog/modify-task-dialog.component';
import {ModifyCommentDialogComponent} from '../dialogs/modify-comment-dialog/modify-comment-dialog.component';



@NgModule({
  declarations: [
    ProjectsComponent,
    ProjectComponent,
    TasksListComponent,
    EditProjectComponent,
    EditTaskComponent,
    TaskComponent,
    CommentsListComponent,
    EditCommentComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgbModule,
    ReactiveFormsModule
  ],
  exports: [
    EditProjectComponent,
    EditTaskComponent,
    EditCommentComponent
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
