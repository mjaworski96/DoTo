import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DeleteAccountDialogComponent} from './delete-account-dialog/delete-account-dialog.component';
import { DeleteProjectDialogComponent } from './delete-project-dialog/delete-project-dialog.component';
import { DeleteTaskDialogComponent } from './delete-task-dialog/delete-task-dialog.component';



@NgModule({
  declarations: [
    DeleteAccountDialogComponent,
    DeleteProjectDialogComponent,
    DeleteTaskDialogComponent
  ],
  imports: [
    CommonModule
  ]
})
export class DialogsModule { }
