import { Injectable } from '@angular/core';
import {TasksService} from './tasks.service';
import {Tasks} from '../../models/task';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TasksResolveService implements Resolve<Tasks> {

  constructor(private tasksService: TasksService,
              private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Tasks> | Promise<Tasks> | Tasks {
    let strId: string;
    strId = route.params.projectId;

    const id = +strId;

    if (isNaN(id)) {
      this.router.navigate(['not-found']);
    } else {
      return this.tasksService
        .getForProject(id)
        .toPromise()
        .then(result => {
          return result;
        });
    }
  }

}
