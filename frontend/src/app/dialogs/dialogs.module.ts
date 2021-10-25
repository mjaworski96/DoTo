import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DeleteAccountDialogComponent} from './delete-account-dialog/delete-account-dialog.component';
import { DeleteProjectDialogComponent } from './delete-project-dialog/delete-project-dialog.component';
import { DeleteTaskDialogComponent } from './delete-task-dialog/delete-task-dialog.component';
import { DeleteCommentDialogComponent } from './delete-comment-dialog/delete-comment-dialog.component';
import { ModifyProjectDialogComponent } from './modify-project-dialog/modify-project-dialog.component';
import {ProjectsModule} from '../projects/projects.module';
import { ModifyTaskDialogComponent } from './modify-task-dialog/modify-task-dialog.component';
import { ModifyCommentDialogComponent } from './modify-comment-dialog/modify-comment-dialog.component';
import { DeleteLabelDialogComponent } from './delete-label-dialog/delete-label-dialog.component';
import { ModifyLabelDialogComponent } from './modify-label-dialog/modify-label-dialog.component';



@NgModule({
  declarations: [
    DeleteAccountDialogComponent,
    DeleteProjectDialogComponent,
    DeleteTaskDialogComponent,
    DeleteCommentDialogComponent,
    ModifyProjectDialogComponent,
    ModifyTaskDialogComponent,
    ModifyCommentDialogComponent,
    DeleteLabelDialogComponent,
    ModifyLabelDialogComponent
  ],
  imports: [
    CommonModule,
    ProjectsModule
  ]
})
export class DialogsModule { }
