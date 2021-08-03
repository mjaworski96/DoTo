import { Injectable } from '@angular/core';
import { Resolve, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { LabelWithIdList } from 'src/app/models/label';
import { LabelsService } from './labels.service';

@Injectable({
  providedIn: 'root'
})
export class LabelsResolveService implements Resolve<LabelWithIdList> {

  constructor(private labelsService: LabelsService,
              private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LabelWithIdList> | Promise<LabelWithIdList> | LabelWithIdList {
    let strId: string;
    strId = route.params.projectId;

    const id = +strId;

    if (isNaN(id)) {
      this.router.navigate(['not-found']);
    } else {
      return this.labelsService
        .getAll(id)
        .toPromise()
        .then(result => {
          return result;
        });
    }
  }
}
