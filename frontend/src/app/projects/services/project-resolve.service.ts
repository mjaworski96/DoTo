import { Injectable } from '@angular/core';
import {ProjectWithId} from '../../models/project';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ProjectsService} from './projects.service';

@Injectable({
  providedIn: 'root'
})
export class ProjectResolveService implements Resolve<ProjectWithId> {

  constructor(private projectsService: ProjectsService,
              private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProjectWithId> | Promise<ProjectWithId> | ProjectWithId {
    let strId: string;
    strId = route.params.projectId;

    const id = +strId;

    if (isNaN(id)) {
      this.router.navigate(['not-found']);
    } else {
      return this.projectsService
        .getOne(id)
        .toPromise()
        .then(result => {
          return result;
        });
    }
  }
}
