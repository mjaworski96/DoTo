import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {User, UserUpdateData} from '../../../models/user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  usersUrl = '/api/users';

  constructor(private httpClient: HttpClient) { }

  getUser(username: string): Observable<User> {
    return this.httpClient.get<User>(
      this.usersUrl + `/${username}`
    );
  }
  updateAccount(username: string, updateData: UserUpdateData): Observable<HttpResponse<User>> {
    return this.httpClient.put<User>(
      `${this.usersUrl}/${username}`,
      updateData,
      {observe: 'response'}
    );
  }
  deleteAccount(username: string): Observable<any> {
    return this.httpClient.delete(
      `${this.usersUrl}/${username}`
    );
  }

}
