import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectWithId } from 'src/app/models/project';
import { ActiveTask } from 'src/app/models/task';

@Component({
  selector: 'app-active-tasks-list',
  templateUrl: './active-tasks-list.component.html',
  styleUrls: ['./active-tasks-list.component.css']
})
export class ActiveTasksListComponent implements OnInit {
  @Input()
  tasks: ActiveTask[];
  
  constructor() { }

  ngOnInit() {
  }

  getProjects(): ProjectWithId[] {
    const projects: ProjectWithId[] = [];

    this.tasks.forEach(task => {
      if (!projects.find(x => x.id === task.project.id)) {
        projects.push(task.project);
      }
    });
    
    return projects;
  }
  getTasksForProject(project: ProjectWithId): ActiveTask[] {
    return this.tasks.filter(x => x.project.id == project.id);
  }
}
