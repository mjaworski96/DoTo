import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectWithId } from 'src/app/models/project';
import { ActiveTask } from 'src/app/models/task';

@Component({
  selector: 'app-active-tasks-from-project-list',
  templateUrl: './active-tasks-from-project-list.component.html',
  styleUrls: ['./active-tasks-from-project-list.component.css']
})
export class ActiveTasksFromProjectListComponent implements OnInit {
  @Input()
  tasks: ActiveTask[];

  @Input()
  project: ProjectWithId;

  expanded = true;

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
