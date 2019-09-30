import {CanActivate, Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {SessionStorageService} from '../shared/services/session-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private sessionStorageService: SessionStorageService,
              private router: Router) {}

  canActivate(): boolean {
    if (this.sessionStorageService.isAdmin()) {
      return true;
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }
}
