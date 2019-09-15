import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SessionStorageService} from '../../shared/services/session-storage.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient,
              private sessionStorage: SessionStorageService) { }

  get(): Observable<any> {
    return this.http.get(`/api/users/${this.sessionStorage.getUsername()}/projects?page=0&size=10`);
  }
}
