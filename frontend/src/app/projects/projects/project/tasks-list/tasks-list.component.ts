import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {TaskWithId} from '../../../../models/task';
import {TasksService} from '../../../services/tasks.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskComponent} from './task/task.component';
import {Router} from "@angular/router";
import {CommentsService} from "../../../services/comments.service";

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.css']
})
export class TasksListComponent implements OnInit {

  @Input()
  tasks: TaskWithId[];

  @Input()
  prevState: string;
  @Input()
  nextState: string;

  @Input()
  currentState: string;

  @Input()
  projectId: number;

  constructor(private tasksService: TasksService,
              private modalService: NgbModal,
              private commentsService: CommentsService) { }

  ngOnInit() {
  }

  updateState(task: TaskWithId, state: string) {
    this.tasksService.updateState(task, {
      name: state
    }).toPromise()
      .then(newState => {
        task.state = newState;
      });
  }
  openTask(task: TaskWithId) {
    this.commentsService.getForTask(task.id)
      .toPromise()
      .then(result => {
        const modalRef = this.modalService.open(TaskComponent, {
          size: 'xl'
        });
        modalRef.componentInstance.task = task;
        modalRef.componentInstance.comments = result;
      });
  }
}
