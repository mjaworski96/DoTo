import { Injectable } from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {SessionStorageService} from './session-storage.service';
import {ToastrService} from 'ngx-toastr';
import {GlobalVariables} from "../../utils/global-variables";

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  constructor(private router: Router,
              private sessionStorageService: SessionStorageService,
              private toastr: ToastrService) { }

  handle(error: HttpErrorResponse): void {
    if (error.error !== undefined && error.error !== null) {
      const duplicate = this.toastr.findDuplicate(error.error.message, false, false);
      if (duplicate != null) {
        this.toastr.remove(duplicate.toastId);
      }
      this.toastr.error(error.error.message, error.error.code, GlobalVariables.toastrConfig);
    }
    if (error.error.code === 404 ||  error.error.code === 504) {
      this.handle404and504();
    } else if (error.error.code === 401) {
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
