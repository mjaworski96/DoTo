import { Component, OnInit } from '@angular/core';
import {SessionStorageService} from "../../shared/services/session-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private sessionStorage: SessionStorageService,
              private router: Router) { }

  ngOnInit() {
  }
  isUserLoggedIn(): boolean {
    return this.sessionStorage.isUserLoggedIn();
  }
  logout(): void {
    this.sessionStorage.logout();
    this.navigate(['/']);
  }
  navigate(commands: any[]): void {
    this.router.navigate(commands);
  }
  isAdmin(): boolean {
    return this.sessionStorage.isAdmin();
  }
  isUser(): boolean {
    return this.sessionStorage.isUser();
  }
}
