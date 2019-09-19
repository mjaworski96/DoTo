import { Injectable } from '@angular/core';
import {ProjectsService} from './projects.service';
import {SessionStorageService} from '../../shared/services/session-storage.service';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {ProjectWithId} from '../../models/project';
import {Page} from '../../models/page';
import {Observable} from 'rxjs';
import {GlobalVariables} from '../../utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class ProjectsResolveService implements Resolve<Page<ProjectWithId>> {

  constructor(private projectService: ProjectsService,
              private sessionStorageService: SessionStorageService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Page<ProjectWithId>> | Promise<Page<ProjectWithId>> | Page<ProjectWithId> {
    const username = this.sessionStorageService.getUsername();
    return this.projectService.get(username, 0, GlobalVariables.projectsPageSize)
      .toPromise()
      .then(result => {
        return result;
      });
  }
}

