import {Component, Input, OnInit} from '@angular/core';
import {TaskWithId} from '../../../../models/task';
import {TasksService} from '../../../services/tasks.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskComponent} from './task/task.component';
import {CommentsService} from "../../../services/comments.service";
import { LabelWithId } from 'src/app/models/label';

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

  @Input()
  projectLabels: LabelWithId[];

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
        modalRef.componentInstance.projectLabels = this.projectLabels;
      });
  }
  getTaskName(task) {
    let labels = '';
    task.labels
      .forEach(x => {
        labels = `${labels}[${x.name}]`;
      });
    
    return `${labels}${task.shortDescription}`;
  }
}
