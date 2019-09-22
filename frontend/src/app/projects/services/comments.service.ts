import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GlobalVariables} from '../../utils/global-variables';
import {Comments} from '../../models/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  getForTask(taskId: number): Observable<Comments> {
    return this.http.get<Comments>(`${GlobalVariables.tasksApi}/${taskId}/${GlobalVariables.commentsApiPostfix}`);
  }
}
