import { Injectable } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  constructor() { }

  handle(error: HttpErrorResponse): void {
    // TODO
  }
}
