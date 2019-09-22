import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {State, Task, TaskWithId, Tasks} from '../../models/task';
import {GlobalVariables} from '../../utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  constructor(private http: HttpClient) { }

  getForProject(projectId: number): Observable<Tasks> {
    return this.http.get<Tasks>(`${GlobalVariables.projectsApi}/${projectId}/${GlobalVariables.tasksApiPostfix}`);
  }
  updateState(task: TaskWithId, state: State): Observable<State> {
    return this.http.put<State>(`${GlobalVariables.tasksApi}/${task.id}/${GlobalVariables.stateApiPostfix}`, state);
  }
  create(projectId: number, task: Task): Observable<TaskWithId> {
    return this.http.post<TaskWithId>(
      `${GlobalVariables.projectsApi}/${projectId}/${GlobalVariables.tasksApiPostfix}`, task);
  }
}
