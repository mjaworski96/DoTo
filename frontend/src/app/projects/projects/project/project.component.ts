import { Component, OnInit } from '@angular/core';
import {ProjectWithId} from '../../../models/project';
import {Tasks, TaskWithId} from '../../../models/tasks';
import {ActivatedRoute, Router} from '@angular/router';
import {DeleteAccountDialogComponent} from "../../../dialogs/delete-account-dialog/delete-account-dialog.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteProjectDialogComponent} from "../../../dialogs/delete-project-dialog/delete-project-dialog.component";
import {ProjectsService} from "../../services/projects.service";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  project: ProjectWithId;
  tasks: Tasks;

  constructor(private route: ActivatedRoute,
              private modalService: NgbModal,
              private projectsService: ProjectsService,
              private router: Router) { }

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
  delete(): void {
    const modalRef = this.modalService.open(DeleteProjectDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.projectsService.delete(
          this.project.id
        ).toPromise().then(result => {
          this.router.navigate(['projects']);
        });
      }
    });
  }
}
