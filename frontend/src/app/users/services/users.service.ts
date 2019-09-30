import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Page} from '../../models/page';
import {Roles, RolesChanges, User} from '../../models/user';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  usersUrl = '/api/users';
  rolesPostfix = 'roles';
  constructor(private httpClient: HttpClient) { }

  get(page: number, size: number) {
    return this.httpClient.get<Page<User>>(`${this.usersUrl}?page=${page}&size=${size}`);
  }
  changeRole(username: string, changes: RolesChanges): Observable<Roles> {
    return this.httpClient
      .patch<Roles>(
        `${this.usersUrl}/${username}/${this.rolesPostfix}`,
        changes
      );
  }
}
