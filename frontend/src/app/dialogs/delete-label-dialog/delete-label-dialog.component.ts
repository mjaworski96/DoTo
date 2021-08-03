import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-label-dialog',
  templateUrl: './delete-label-dialog.component.html',
  styleUrls: ['./delete-label-dialog.component.css']
})
export class DeleteLabelDialogComponent implements OnInit {

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  close(result: any) {
    this.activeModal.close(result);
  }

}
