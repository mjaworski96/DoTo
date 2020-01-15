import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {SessionStorageService} from './shared/services/session-storage.service';
import {ErrorHandlingService} from './shared/services/error-handling.service';
import {tap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Interceptor  implements HttpInterceptor {

  constructor(private sessionStorageService: SessionStorageService,
              private errorHandlingService: ErrorHandlingService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.sessionStorageService.isUserLoggedIn()) {
      request = request.clone({
        setHeaders: {
          Authorization: this.sessionStorageService.getToken()
        }
      });
    }
    return next.handle(request).pipe(
      tap(
        result => this.handleValidResponse(result),
          error => this.handleErrorResponse(error))
    );
  }
  handleValidResponse(result: HttpEvent<any>): void {
    if (result instanceof HttpResponse) {
      const response = result as HttpResponse<any>;
      if (response.headers.get('Authorization') !== null) {
        this.sessionStorageService.updateSession(response.headers.get('Authorization'));
      }
    }
  }
  handleErrorResponse(error: any): Observable<any> {
    this.errorHandlingService.handle(error);
    return of(error);
  }
}
