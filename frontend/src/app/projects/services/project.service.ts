import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProjectWithId} from '../../models/project';
import {Page} from '../../models/page';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) { }

  get(username: string, page: number, size: number): Observable<Page<ProjectWithId>> {
    return this.http.get<Page<ProjectWithId>>(`/api/users/${username}/projects?page=${page}&size=${size}`);
  }
}
