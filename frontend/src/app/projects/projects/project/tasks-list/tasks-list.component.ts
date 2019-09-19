import {Component, Input, OnInit} from '@angular/core';
import {TaskWithId} from '../../../../models/tasks';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.css']
})
export class TasksListComponent implements OnInit {

  @Input()
  tasks: TaskWithId[];

  constructor() { }

  ngOnInit() {
  }

}
