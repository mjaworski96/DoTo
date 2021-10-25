import { Injectable } from '@angular/core';
import {User} from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  constructor() { }

  setSession(user: User, token: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('userData', JSON.stringify(user));
  }
  getUser(): User {
    return JSON.parse(localStorage.getItem('userData'));
  }
  getUserId(): number {
    return this.getUser().id;
  }
  isUserLoggedIn(): boolean {
    return this.getUser() !== null;
  }
  getToken(): string {
    return localStorage.getItem('token');
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userData');
  }
  hasRole(role: string): boolean {
    if (!this.isUserLoggedIn() || !this.getUser().roles) {
      return false;
    }
    for (let i = 0; i < this.getUser().roles.length; i++) {
      if (this.getUser().roles[i].name === role) {
        return true;
      }
    }
    return false;
  }
  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }
  isUser(): boolean {
    return this.hasRole('USER');
  }
}
