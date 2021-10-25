import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActiveTaskList } from 'src/app/models/task';
import { GlobalVariables } from 'src/app/utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class ActiveTasksService {

  constructor(private http: HttpClient) { }

  get(userId: number): Observable<ActiveTaskList> {
    return this.http.get<ActiveTaskList>(
      `${GlobalVariables.usersApi}/${userId}/${GlobalVariables.tasksApiPostfix}`);
  }
}
