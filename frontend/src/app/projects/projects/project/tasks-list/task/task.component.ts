import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskWithId} from '../../../../../models/task';
import {DeleteProjectDialogComponent} from '../../../../../dialogs/delete-project-dialog/delete-project-dialog.component';
import {TasksService} from '../../../../services/tasks.service';
import {Router} from '@angular/router';
import {ModifyTaskDialogComponent} from '../../../../../dialogs/modify-task-dialog/modify-task-dialog.component';
import {Comments} from '../../../../../models/comment';
import { LabelWithId } from 'src/app/models/label';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  @Input()
  task: TaskWithId;
  @Input()
  comments: Comments;
  @Input()
  projectLabels: LabelWithId[];
  
  constructor(private activeModal: NgbActiveModal,
              private modalService: NgbModal,
              private tasksService: TasksService,
              private router: Router) { }

  ngOnInit() {
  }

  delete() {
    const modalRef = this.modalService.open(DeleteProjectDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.tasksService.delete(
          this.task.id
        ).toPromise().then(result => {
          this.router.navigate(['/', 'projects', this.task.project.id]);
          this.activeModal.close();
        });
      }
    }).catch(error => {
      // prevent error in console
    });
  }

  modify() {
    const modalRef = this.modalService.open(ModifyTaskDialogComponent, {
      size: 'xl'
    });
    modalRef.componentInstance.task = this.task;
    modalRef.componentInstance.projectLabels = this.projectLabels;
    modalRef.result.then(modifiedTask => {
      if (modifiedTask !== undefined) {
        this.task.shortDescription = modifiedTask.shortDescription;
        this.task.fullDescription = modifiedTask.fullDescription;
        this.task.labels = modifiedTask.labels;
      }
    }).catch(error => {
      // prevent error in console
    });
  }
  getTaskName() {
    let labels = '';
    this.task.labels
      .forEach(x => {
        labels = `${labels}[${x.name}]`;
      });
    
    return `${labels}${this.task.shortDescription}`;
  }

  dismiss() {
    this.activeModal.dismiss();
  }
}
