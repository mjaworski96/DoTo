import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LabelWithId } from 'src/app/models/label';

@Component({
  selector: 'app-modify-label-dialog',
  templateUrl: './modify-label-dialog.component.html',
  styleUrls: ['./modify-label-dialog.component.css']
})
export class ModifyLabelDialogComponent implements OnInit {

  @Input()
  label: LabelWithId

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  closeWindow(label: LabelWithId) {
    this.activeModal.close(label);
  }

}
