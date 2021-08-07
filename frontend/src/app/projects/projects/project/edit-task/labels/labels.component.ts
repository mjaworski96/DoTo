import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { LabelWithId } from 'src/app/models/label';

@Component({
  selector: 'app-labels',
  templateUrl: './labels.component.html',
  styleUrls: ['./labels.component.css']
})
export class LabelsComponent implements OnInit, OnChanges {

  @Input()
  availableLabels: LabelWithId[];

  @Input()
  currentlySelected: LabelWithId[];

  labelsForm: FormArray;

  @Input()
  parentForm: FormGroup;

  constructor() { }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes.availableLabels) {
      this.parentForm.removeControl('labels');
      this.createLabelsArray();
    }
  }

  ngOnInit() {
    this.createLabelsArray();
  }
  createLabelsArray() {
    this.labelsForm = new FormArray([]);
    this.parentForm.addControl('labels', this.labelsForm);
    
    this.availableLabels
    .forEach(item => {
      this.labelsForm.push(
        new FormGroup({
          id: new FormControl(item.id),
          name: new FormControl(item.name),
          selected: new FormControl(this.isSelected(item.id))
        })
      );
    })
  }
  isSelected(id: number): boolean {
    if (!this.currentlySelected) {
      return false;
    }
    return !!this.currentlySelected.find(x => x.id === id);
  }
}
