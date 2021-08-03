import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Label, LabelWithId, LabelWithIdList } from 'src/app/models/label';
import { GlobalVariables } from 'src/app/utils/global-variables';

@Injectable({
  providedIn: 'root'
})
export class LabelsService {

  constructor(private http: HttpClient) { }

  getAll(projectId: number): Observable<LabelWithIdList> {
    return this.http.get<LabelWithIdList>(`${GlobalVariables.projectsApi}/${projectId}/${GlobalVariables.labelApiPostfix}`);;
  }
  create(projectId: number, label: Label): Observable<LabelWithId> {
    return this.http.post<LabelWithId>(`${GlobalVariables.projectsApi}/${projectId}/${GlobalVariables.labelApiPostfix}`, label);
  }
  update(labelId: number, label: Label): Observable<LabelWithId> {
    return this.http.put<LabelWithId>(`${GlobalVariables.labelsApi}/${labelId}`, label);
  }
  delete(labelId: number): Observable<any> {
    return this.http.delete(`${GlobalVariables.labelsApi}/${labelId}`);
  }
}
