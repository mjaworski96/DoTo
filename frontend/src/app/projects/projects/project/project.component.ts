import { Component, OnInit } from '@angular/core';
import {ProjectWithId} from '../../../models/project';
import {Tasks, TaskWithId} from '../../../models/tasks';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  project: ProjectWithId;
  tasks: Tasks;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.project = this.route.snapshot.data.project;
    this.tasks = this.route.snapshot.data.tasks;
  }
  filter(stateName: string): TaskWithId[] {
    return this.tasks.tasks.filter(item => item.state.name === stateName);
  }
  addTask(task: TaskWithId) {
    this.tasks.tasks.push(task);
  }
}
