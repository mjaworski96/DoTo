import { Injectable } from '@angular/core';
import {NgxSpinnerService} from 'ngx-spinner';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  constructor(private spinner: NgxSpinnerService) { }
  start(): void {
    this.spinner.show();
  }
  end(): void {
    this.spinner.hide();
  }
}
