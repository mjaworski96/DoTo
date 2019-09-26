import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskWithId} from '../../../../../models/task';
import {DeleteProjectDialogComponent} from '../../../../../dialogs/delete-project-dialog/delete-project-dialog.component';
import {TasksService} from '../../../../services/tasks.service';
import {Router} from '@angular/router';
import {ModifyTaskDialogComponent} from '../../../../../dialogs/modify-task-dialog/modify-task-dialog.component';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  @Input()
  task: TaskWithId;

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
    modalRef.componentInstance.taskId = this.task.id;
    modalRef.result.then(modifiedTask => {
      if (modifiedTask !== undefined) {
        this.task.shortDescription = modifiedTask.shortDescription;
        this.task.fullDescription = modifiedTask.fullDescription;
      }
    }).catch(error => {
      // prevent error in console
    });
  }
}
