import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../../utils/global-variables';
import {TasksService} from '../../../services/tasks.service';
import {TaskWithId} from '../../../../models/task';
import {finalize} from 'rxjs/operators';
import { LabelWithIdList } from 'src/app/models/label';
import { LabelsComponent } from './labels/labels.component';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {
  @Input()
  projectId: number;

  @Input()
  task: TaskWithId;

  @Input()
  projectLabels: LabelWithIdList;

  @ViewChild('labelsEdit', {static: true})
  labelsEditControl: LabelsComponent;

  editTaskForm: FormGroup;

  minShortDescriptionLength = GlobalVariables.minTaskShortDescriptionLength;
  maxShortDescriptionLength = GlobalVariables.maxTaskShortDescriptionLength;
  maxFullDescriptionLength = GlobalVariables.maxTaskFullDescriptionLength;
  textAreaRows = GlobalVariables.textAreaRows;
  
  processing = false;
  
  @Output()
  newTask = new EventEmitter<TaskWithId>();

  constructor(private formBuilder: FormBuilder,
              private tasksService: TasksService) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.editTaskForm = this.formBuilder.group({
      shortDescription: ['', [
        Validators.required,
        Validators.minLength(this.minShortDescriptionLength),
        Validators.maxLength(this.maxShortDescriptionLength)
      ]],
      fullDescription: ['', [
        Validators.maxLength(this.maxFullDescriptionLength)
      ]]
    });
    this.setValuesForModification();
  }
  setValuesForModification(): void {
    if (this.task !== undefined) {
      this.editTaskForm.controls.shortDescription.setValue(this.task.shortDescription);
      this.editTaskForm.controls.fullDescription.setValue(this.task.fullDescription);
    }
  }

  edit() {
    if (this.task === undefined) {
      this.add();
    } else {
      this.modify();
    }
  }
  add() {
    this.processing = true;
    this.tasksService.create(
      this.projectId,
      this.getFormValue()
    ).pipe(
      finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.resetForm();
        this.newTask.emit(result);
    });
  }
  modify() {
    this.processing = true;
    this.tasksService.update(
      this.task.id,
      this.getFormValue()
    ).pipe(
        finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.newTask.emit(result);
      });
  }
  getFormValue() {
    const value = this.editTaskForm.value;
    value.labels = value.labels
      .filter(x => x.selected)
      .map(x => {
        return {
          id: x.id,
          name: x.name
        };
      });
    return value; 
  }
  resetForm() {
    this.editTaskForm.reset();
    this.labelsEditControl.createLabelsArray();
  }
  getCurrentlySelectedLabels() {
    if (!this.task) {
      return null;
    }
    return this.task.labels;
  }
}
