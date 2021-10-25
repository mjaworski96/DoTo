import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { finalize } from 'rxjs/operators';
import { LabelWithId } from 'src/app/models/label';
import { LabelsService } from 'src/app/projects/services/labels.service';
import { GlobalVariables } from 'src/app/utils/global-variables';

@Component({
  selector: 'app-edit-label',
  templateUrl: './edit-label.component.html',
  styleUrls: ['./edit-label.component.css']
})
export class EditLabelComponent implements OnInit {

  @Input() projectId: number;
  @Input() label: LabelWithId;
  @Output()
  newLabel = new EventEmitter<LabelWithId>();

  editLabelForm: FormGroup;

  maxLabelNameLength = GlobalVariables.maxLabelNameLength;
  processing = false;
  
  constructor(private labelsService: LabelsService,
              private formBuilder: FormBuilder) { }


  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.editLabelForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.maxLength(this.maxLabelNameLength)
      ]]
    });
    this.setDefaultValues();
  }
  setDefaultValues(): void {
    if (this.label !== undefined) {
      this.editLabelForm.controls.name.setValue(this.label.name);
    }
  }
  edit() {
    if (this.label === undefined) {
      this.add();
    } else {
      this.modify();
    }
  }
  add() {
    this.processing = true;
    this.labelsService.create(
      this.projectId,
      this.editLabelForm.value
    ).pipe(
      finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.editLabelForm.reset();
        this.newLabel.emit(result);
      });
  }
  modify() {
    this.processing = true;
    this.labelsService.update(
      this.label.id,
      this.editLabelForm.value
    ).pipe(
      finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.newLabel.emit(result);
      });
  }
}
