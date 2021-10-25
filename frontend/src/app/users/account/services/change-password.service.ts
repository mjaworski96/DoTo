import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PasswordChange} from '../../../models/user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  constructor(private httpClient: HttpClient) { }

  changePassword(userId: number, passwordChange: PasswordChange): Observable<any> {
    return this.httpClient.post(
      `/api/users/${userId}/password`,
      passwordChange
    );
  }
}
