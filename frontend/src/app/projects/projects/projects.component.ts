import { Component, OnInit } from '@angular/core';
import {ProjectsService} from '../services/projects.service';
import {ProjectWithId} from '../../models/project';
import {Page} from '../../models/page';
import {ActivatedRoute, NavigationEnd, Router, RouterEvent} from '@angular/router';
import {GlobalVariables} from '../../utils/global-variables';
import {SessionStorageService} from '../../shared/services/session-storage.service';
import {Subject} from 'rxjs';
import {filter, takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {
  destroyed = new Subject<any>();
  projectsPage: Page<ProjectWithId>;
  pageSize = GlobalVariables.projectsPageSize;
  archived: boolean;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectsService,
              private sessionStorageService: SessionStorageService,
              private router: Router) { }

  ngOnInit() {
    this.projectsPage = this.route.snapshot.data.projects;
    this.archived = this.route.snapshot.data.archived;
    this.reloadPageIfRouted();
  }
  reloadPageIfRouted() {
    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd),
      takeUntil(this.destroyed))
      .subscribe(res => {
        this.projectsPage = this.route.snapshot.data.projects;
      });
  }
  updatePage(pageNumber: any): void {
    this.projectService.get(
      this.sessionStorageService.getUsername(), this.archived,
      pageNumber - 1, this.pageSize
    ).toPromise()
      .then(page => this.projectsPage = page);
  }
  goToProject(projectId: number) {
    this.router.navigate(['projects', projectId]);
  }
}
