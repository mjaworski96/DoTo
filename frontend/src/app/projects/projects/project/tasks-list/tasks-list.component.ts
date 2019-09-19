import {Component, Input, OnInit} from '@angular/core';
import {TaskWithId} from '../../../../models/tasks';
import {TasksService} from "../../../services/tasks.service";

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

  constructor(private tasksService: TasksService) { }

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
}
