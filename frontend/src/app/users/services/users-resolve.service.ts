import { Injectable } from '@angular/core';
import {UserService} from "../account/services/user.service";
import {SessionStorageService} from "../../shared/services/session-storage.service";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../models/user";
import {UsersService} from "./users.service";
import {Page} from "../../models/page";
import {GlobalVariables} from "../../utils/global-variables";

@Injectable({
  providedIn: 'root'
})
export class UsersResolveService implements Resolve<Page<User>> {

  constructor(private usersService: UsersService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Page<User>> | Promise<Page<User>> | Page<User> {
    return this.usersService.get(0, GlobalVariables.usersPageSize)
      .toPromise()
      .then(result => {
        return result;
      });
  }
}
