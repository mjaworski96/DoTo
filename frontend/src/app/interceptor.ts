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
import {finalize, tap} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {Observable} from 'rxjs';
import {LoadingService} from './shared/services/loading.service';

@Injectable({
  providedIn: 'root'
})
export class Interceptor  implements HttpInterceptor {

  constructor(private sessionStorageService: SessionStorageService,
              private errorHandlingService: ErrorHandlingService,
              private loadingService: LoadingService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.sessionStorageService.isUserLoggedIn()) {
      request = request.clone({
        setHeaders: {
          Authorization: this.sessionStorageService.getToken()
        }
      });
    }
    this.loadingService.start();
    return next.handle(request).pipe(
      finalize(() => this.loadingService.end()),
      tap(
        result => this.handleValidResponse(result),
          error => this.errorHandlingService.handle(error))
    );
  }
  handleValidResponse(result: HttpEvent<any>): void {
    if (result instanceof HttpResponse) {
      const response = result as HttpResponse<any>;
      if (response.headers.get('Authorization') !== null && response.headers.get('X-User') !== null) {
        const userJson = response.headers.get('X-User');
        const user = JSON.parse(userJson);
        this.sessionStorageService.setSession(user, response.headers.get('Authorization'));
      }
    }
  }
}
