import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {User} from '../../../models/user';
import {Observable} from 'rxjs';
import {UserService} from './user.service';
import {SessionStorageService} from '../../../shared/services/session-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserResolveService implements Resolve<User> {

  constructor(private userService: UserService,
              private sessionStorageService: SessionStorageService,
              private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
    const userId = this.sessionStorageService.getUserId()
    if (!userId) {
      this.router.navigate(['not-found']);
      return null;
    }
    return this.userService.getUser(userId)
      .toPromise()
      .then(result => {
        return result;
      });
  }
}
