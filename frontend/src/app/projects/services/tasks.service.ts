import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Tasks} from '../../models/tasks';
import {GlobalVariables} from '../../utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  constructor(private http: HttpClient) { }

  getForProject(projectId: number): Observable<Tasks> {
    return this.http.get<Tasks>(`${GlobalVariables.projectsApi}/${projectId}/${GlobalVariables.tasksApiPostfix}`);
  }
}
