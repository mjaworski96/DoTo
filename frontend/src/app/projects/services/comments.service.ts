import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GlobalVariables} from '../../utils/global-variables';
import {Comment, Comments, CommentWithId} from '../../models/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  getForTask(taskId: number): Observable<Comments> {
    return this.http.get<Comments>(`${GlobalVariables.tasksApi}/${taskId}/${GlobalVariables.commentsApiPostfix}`);
  }
  create(taskId: number, comment: Comment): Observable<CommentWithId> {
    return this.http.post<CommentWithId>(`${GlobalVariables.tasksApi}/${taskId}/${GlobalVariables.commentsApiPostfix}`, comment);
  }
  delete(commentId: number): Observable<any> {
    return this.http.delete<any>(`${GlobalVariables.commentsApi}/${commentId}`);
  }
  update(commentId: number, comment: Comment): Observable<CommentWithId> {
    return this.http.put<CommentWithId>(`${GlobalVariables.commentsApi}/${commentId}`, comment);
  }
}
