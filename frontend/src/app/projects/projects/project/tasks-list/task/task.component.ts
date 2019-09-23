import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskWithId} from '../../../../../models/task';
import {DeleteProjectDialogComponent} from "../../../../../dialogs/delete-project-dialog/delete-project-dialog.component";
import {CommentsService} from "../../../../services/comments.service";
import {TasksService} from "../../../../services/tasks.service";

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
              private tasksService: TasksService) { }

  ngOnInit() {
  }

  delete() {
    const modalRef = this.modalService.open(DeleteProjectDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.tasksService.delete(
          this.task.id
        ).toPromise().then(result => {
          this.activeModal.close(true);
        });
      }
    });
  }
}
