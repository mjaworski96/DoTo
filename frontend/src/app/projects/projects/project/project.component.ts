import { Component, OnInit } from '@angular/core';
import {ProjectWithId} from '../../../models/project';
import {Tasks, TaskWithId} from '../../../models/task';
import {ActivatedRoute, NavigationEnd, Router, RouterEvent} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DeleteProjectDialogComponent} from '../../../dialogs/delete-project-dialog/delete-project-dialog.component';
import {ProjectsService} from '../../services/projects.service';
import {filter, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {ModifyProjectDialogComponent} from '../../../dialogs/modify-project-dialog/modify-project-dialog.component';
import { LabelWithIdList } from 'src/app/models/label';
import { LabelListComponent } from './label-list/label-list.component';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  project: ProjectWithId;
  tasks: Tasks;
  projectLabels: LabelWithIdList;
  destroyed = new Subject<any>();

  constructor(private route: ActivatedRoute,
              private modalService: NgbModal,
              private projectsService: ProjectsService,
              private router: Router) { }

  ngOnInit() {
    this.project = this.route.snapshot.data.project;
    this.tasks = this.route.snapshot.data.tasks;
    this.projectLabels = this.route.snapshot.data.labels;
    this.reloadPageIfRouted();
  }
  reloadPageIfRouted() {
    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd),
      takeUntil(this.destroyed))
      .subscribe(res => {
        this.project = this.route.snapshot.data.project;
        this.tasks = this.route.snapshot.data.tasks;
      });
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
    }).catch(error => {
      // prevent error in console
    });
  }
  modify() {
    const modalRef = this.modalService.open(ModifyProjectDialogComponent, {
      size: 'xl'
    });
    modalRef.componentInstance.project = this.project;
    modalRef.result.then(modifiedProject => {
      if (modifiedProject !== undefined) {
        this.project = modifiedProject;
      }
    }).catch(error => {
      // prevent error in console
    });
  }
  modifyArchived() {
    this.projectsService.updateArchived(
      this.project.id,
      {
        archived: !this.project.archived
      }
    ).toPromise()
      .then(newState => {
        this.project.archived = newState.archived;
      });
  }
  showLabels() {
    const modalRef = this.modalService.open(LabelListComponent, {
      size: 'xl'
    });
    modalRef.componentInstance.labels = this.projectLabels;
    modalRef.componentInstance.projectId = this.project.id;
  }
}
