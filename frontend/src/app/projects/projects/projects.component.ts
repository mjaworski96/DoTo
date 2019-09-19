import { Component, OnInit } from '@angular/core';
import {ProjectsService} from "../services/projects.service";
import {ProjectWithId} from "../../models/project";
import {Page} from "../../models/page";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalVariables} from "../../utils/global-variables";
import {SessionStorageService} from "../../shared/services/session-storage.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  projectsPage: Page<ProjectWithId>;
  pageSize = GlobalVariables.projectsPageSize;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectsService,
              private sessionStorageService: SessionStorageService,
              private router: Router) { }

  ngOnInit() {
    this.projectsPage = this.route.snapshot.data.projects;
  }
  updatePage(pageNumber: any): void {
    this.projectService.get(
      this.sessionStorageService.getUsername(),
      pageNumber - 1, this.pageSize
    ).toPromise()
      .then(page => this.projectsPage = page);
  }
  goToProject(projectId: number) {
    this.router.navigate(['projects', projectId]);
  }
}
