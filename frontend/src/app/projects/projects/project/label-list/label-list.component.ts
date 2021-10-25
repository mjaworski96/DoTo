import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteLabelDialogComponent } from 'src/app/dialogs/delete-label-dialog/delete-label-dialog.component';
import { ModifyLabelDialogComponent } from 'src/app/dialogs/modify-label-dialog/modify-label-dialog.component';
import { LabelWithId, LabelWithIdList } from 'src/app/models/label';
import { LabelsService } from 'src/app/projects/services/labels.service';

@Component({
  selector: 'app-label-list',
  templateUrl: './label-list.component.html',
  styleUrls: ['./label-list.component.css']
})
export class LabelListComponent implements OnInit {

  @Input() labels: LabelWithIdList;
  @Input() projectId: number;
  @Output()
  deleteLabel = new EventEmitter<number>();
  @Output()
  editLabel = new EventEmitter<LabelWithId>();

  constructor(private labelsService: LabelsService,
              private modalService: NgbModal,
              private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }
  dismiss() {
    this.activeModal.dismiss();
  }
  addLabel(label: LabelWithId) {
    this.labels.labels = [...this.labels.labels, label]
  }
  modify(label: LabelWithId) {
    const modalRef = this.modalService.open(ModifyLabelDialogComponent, {
      size: 'xl'
    });
    modalRef.componentInstance.label = label;

    modalRef.result.then(modifiedLabel => {
      if (modifiedLabel !== undefined) {
        label.name = modifiedLabel.name;
        this.labels.labels = [...this.labels.labels];
        this.editLabel.emit(label);
      }
    }).catch(error => {
      // prevent error in console
    });
  }
  delete(label: LabelWithId): void {
    const modalRef = this.modalService.open(DeleteLabelDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.labelsService.delete(
          label.id
        ).toPromise().then(result => {
          this.labelsService.getAll(this.projectId)
            .toPromise()
            .then(labels => {
              this.labels.labels = labels.labels;
              this.deleteLabel.emit(label.id);
            });
        });
      }
    }).catch(error => {
      // prevent error in console
    });
  }
}
