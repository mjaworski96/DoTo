import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {SessionStorageService} from '../../../shared/services/session-storage.service';
import {ErrorHandlingService} from '../../../shared/services/error-handling.service';
import {Router} from '@angular/router';
import {LoggedUser, LoginDetails, RegisterUserDetails} from "../../../models/user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient,
              private sessionStorage: SessionStorageService,
              private errorHandlingService: ErrorHandlingService,
              private router: Router) { }
  handleValidUser(response: HttpResponse <LoggedUser>): void {
    this.sessionStorage.storeSession(response.body,
      response.headers.get('Authorization'));
    this.router.navigate(['projects']);
  }
  login(loginDetails: LoginDetails): void {
    this.httpClient.post('/api/login', loginDetails, {observe: 'response'})
      .toPromise().then( (response: HttpResponse <LoggedUser>) => {
      this.handleValidUser(response);
    });
  }
  register(registerDetails: RegisterUserDetails): void {
    this.httpClient.post('/api/users', registerDetails, {observe: 'response'})
      .toPromise().then( (response: HttpResponse <LoggedUser>) => {
      this.handleValidUser(response);
    });
  }

}
