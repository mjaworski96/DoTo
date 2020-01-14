import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Project, ProjectWithId} from '../../models/project';
import {Page} from '../../models/page';
import {GlobalVariables} from '../../utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  constructor(private http: HttpClient) { }

  get(username: string, archived: boolean, page: number, size: number): Observable<Page<ProjectWithId>> {
    return this.http.get<Page<ProjectWithId>>(
      `${GlobalVariables.usersApi}/${username}/${GlobalVariables.projectApiPostfix}?archived=${archived}&page=${page}&size=${size}`);
  }
  getOne(id: number): Observable<ProjectWithId> {
    return this.http.get<ProjectWithId>(
      `${GlobalVariables.projectsApi}/${id}`
    );
  }
  create(username: string, project: Project): Observable<ProjectWithId> {
    return this.http.post<ProjectWithId>(
      `${GlobalVariables.usersApi}/${username}/${GlobalVariables.projectApiPostfix}`, project);
  }
  delete(id: number): Observable<any> {
    return this.http.delete<any>(
      `${GlobalVariables.projectsApi}/${id}`
    );
  }
  update(projectId: number, project: Project): Observable<ProjectWithId> {
    return this.http.put<ProjectWithId>(
      `${GlobalVariables.projectsApi}/${projectId}`, project);
  }
}
