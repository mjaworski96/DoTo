import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActiveTask } from 'src/app/models/task';

@Component({
  selector: 'app-active-tasks-list',
  templateUrl: './active-tasks-list.component.html',
  styleUrls: ['./active-tasks-list.component.css']
})
export class ActiveTasksListComponent implements OnInit {
  @Input()
  tasks: ActiveTask[];
  
  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToTask(task: ActiveTask) {
    this.router.navigate(['projects', task.project.id], {
      queryParams: {
        task: task.id
      }
    });
  }
}
