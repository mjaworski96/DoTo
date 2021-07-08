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

  getUser(userId: number): Observable<User> {
    return this.httpClient.get<User>(
      `${this.usersUrl}/${userId}`
    );
  }
  updateAccount(userId: number, updateData: UserUpdateData): Observable<HttpResponse<User>> {
    return this.httpClient.put<User>(
      `${this.usersUrl}/${userId}`,
      updateData,
      {observe: 'response'}
    );
  }
  deleteAccount(userId: number): Observable<any> {
    return this.httpClient.delete(
      `${this.usersUrl}/${userId}`
    );
  }

}
