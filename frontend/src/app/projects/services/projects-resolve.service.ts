import { Injectable } from '@angular/core';
import {ProjectsService} from './projects.service';
import {SessionStorageService} from '../../shared/services/session-storage.service';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {ProjectWithId} from '../../models/project';
import {Page} from '../../models/page';
import {Observable} from 'rxjs';
import {GlobalVariables} from '../../utils/global-variables';

export class ProjectsResolveService implements Resolve<Page<ProjectWithId>> {

  constructor(private projectService: ProjectsService,
              private sessionStorageService: SessionStorageService,
              private archived: boolean) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Page<ProjectWithId>> | Promise<Page<ProjectWithId>> | Page<ProjectWithId> {
    const username = this.sessionStorageService.getUsername();
    return this.projectService.get(username, this.archived, 0, GlobalVariables.projectsPageSize)
      .toPromise()
      .then(result => {
        return result;
      });
  }
}

@Injectable({
  providedIn: 'root'
})
export class NotArchivedProjectsResolveService extends ProjectsResolveService{
  constructor(projectService: ProjectsService,
              sessionStorageService: SessionStorageService) {
    super(projectService, sessionStorageService, false);
  }
}

@Injectable({
  providedIn: 'root'
})
export class ArchivedProjectsResolveService extends ProjectsResolveService{
  constructor(projectService: ProjectsService,
              sessionStorageService: SessionStorageService) {
    super(projectService, sessionStorageService, true);
  }
}
