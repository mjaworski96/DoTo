import { Injectable } from '@angular/core';
import {LoggedUser} from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  storeSession(user: LoggedUser, token: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('userData', JSON.stringify(user));
  }
  getUser(): LoggedUser {
    return JSON.parse(localStorage.getItem('userData'));
  }
  getUsername(): string {
    return this.getUser().username;
  }
  isUserLoggedIn(): boolean {
    return this.getUser() !== null;
  }
  getToken(): string {
    return localStorage.getItem('token');
  }
  updateSession(token: string): void {
    localStorage.setItem('token', token);
  }
}
