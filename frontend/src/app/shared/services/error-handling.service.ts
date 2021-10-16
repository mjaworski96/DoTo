import { Injectable } from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {SessionStorageService} from './session-storage.service';
import {ToastrService} from 'ngx-toastr';
import {GlobalVariables} from '../../utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  constructor(private router: Router,
              private sessionStorageService: SessionStorageService,
              private toastr: ToastrService) { }

  handle(error: HttpErrorResponse): void {
    if (error.error && error.error) {
      const duplicate = this.toastr.findDuplicate(error.error.message, false, false);
      if (duplicate != null) {
        this.toastr.remove(duplicate.toastId);
      }
      if (error.error.message) {
        this.toastr.error(error.error.message, '', GlobalVariables.toastrConfig);
      }   
    }
    if (error.status === 404 ||  error.status === 504) {
      this.handle404and504();
    } else if (error.status === 401) {
      this.handle401();
    }
  }
  handle401(): void {
    this.sessionStorageService.logout();
    this.router.navigate(['login']);
  }
  handle404and504(): void {
    this.router.navigate(['not-found']);
  }
}
