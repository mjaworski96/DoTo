import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ActiveTaskList } from 'src/app/models/task';
import { SessionStorageService } from 'src/app/shared/services/session-storage.service';
import { ActiveTasksService } from './active-tasks.service';

@Injectable({
  providedIn: 'root'
})
export class ActiveTasksResolveService implements Resolve<ActiveTaskList> {

  constructor(private activeTasksService: ActiveTasksService,
              private sessionStorageService: SessionStorageService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ActiveTaskList> | Promise<ActiveTaskList> | ActiveTaskList {
    if (this.sessionStorageService.isUserLoggedIn()) {
      const userId = this.sessionStorageService.getUserId();
      return this.activeTasksService.get(userId)
        .toPromise()
        .then(result => {
          return result;
        });
    } else {
      return null;
    }
  }
}
