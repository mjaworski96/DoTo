import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DeleteAccountDialogComponent} from './delete-account-dialog/delete-account-dialog.component';
import { DeleteProjectDialogComponent } from './delete-project-dialog/delete-project-dialog.component';
import { DeleteTaskDialogComponent } from './delete-task-dialog/delete-task-dialog.component';
import { DeleteCommentDialogComponent } from './delete-comment-dialog/delete-comment-dialog.component';
import { ModifyProjectDialogComponent } from './modify-project-dialog/modify-project-dialog.component';
import {ProjectsModule} from '../projects/projects.module';



@NgModule({
  declarations: [
    DeleteAccountDialogComponent,
    DeleteProjectDialogComponent,
    DeleteTaskDialogComponent,
    DeleteCommentDialogComponent,
    ModifyProjectDialogComponent
  ],
  imports: [
    CommonModule,
    ProjectsModule
  ]
})
export class DialogsModule { }
