import {Component, Input, OnInit} from '@angular/core';
import {TaskWithId} from '../../../../models/tasks';
import {TasksService} from '../../../services/tasks.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskComponent} from './task/task.component';

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

  constructor(private tasksService: TasksService,
              private modalService: NgbModal) { }

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
    const modalRef = this.modalService.open(TaskComponent, {
      size: 'xl'
    });
    modalRef.componentInstance.task = task;
  }
}
