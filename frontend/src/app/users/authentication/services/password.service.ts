import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ResetPassword} from '../../../models/user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  constructor(private httpClient: HttpClient) { }

  resetPassword(data: ResetPassword): Observable<any> {
    return this.httpClient.post('/api/password/reset', data);
  }
}
