import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {SessionStorageService} from '../../../shared/services/session-storage.service';
import {ErrorHandlingService} from '../../../shared/services/error-handling.service';
import {Router} from '@angular/router';
import {LoginDetails, RegisterUserDetails, User} from '../../../models/user';
import {finalize} from 'rxjs/operators';
import {LoginComponent} from '../login/login.component';
import {RegisterComponent} from '../register/register.component';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient,
              private sessionStorage: SessionStorageService,
              private errorHandlingService: ErrorHandlingService,
              private router: Router) { }
  handleValidUser(response: HttpResponse <User>): void {
    this.sessionStorage.storeSession(response.body,
      response.headers.get('Authorization'));
    if (this.sessionStorage.isUser()) {
      this.router.navigate(['projects']);
    } else if (this.sessionStorage.isAdmin())  {
      this.router.navigate(['users']);
    } else {
      this.router.navigate(['not-found']);
    }
  }

  login(loginDetails: LoginDetails, finalizeCallback: () => void, controller: LoginComponent): void {
    this.httpClient.post('/api/login', loginDetails, {observe: 'response'})
      .pipe(
        finalize(() => finalizeCallback.call(controller)))
      .toPromise()
      .then( (response: HttpResponse<User>) => {
      this.handleValidUser(response);
    });
  }

  register(registerDetails: RegisterUserDetails, finalizeCallback: () => void, controller: RegisterComponent): void {
    this.httpClient.post('/api/users', registerDetails, {observe: 'response'})
      .pipe(
        finalize(() => finalizeCallback.call(controller)))
      .toPromise()
      .then((response: HttpResponse<User>) => {
      this.handleValidUser(response);
    });
  }

}
