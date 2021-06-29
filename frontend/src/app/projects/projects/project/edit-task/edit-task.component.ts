import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../../utils/global-variables';
import {TasksService} from '../../../services/tasks.service';
import {TaskWithId} from '../../../../models/task';
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-add-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {
  @Input()
  projectId: number;

  @Input()
  task: TaskWithId;

  editTaskForm: FormGroup;

  minShortDescriptionLength = GlobalVariables.minTaskShortDescriptionLength;
  maxShortDescriptionLength = GlobalVariables.maxTaskShortDescriptionLength;
  maxFullDescriptionLength = GlobalVariables.maxTaskFullDescriptionLength;
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
      ]],
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
      this.editTaskForm.value
    ).pipe(
      finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.editTaskForm.reset();
        this.newTask.emit(result);
    });
  }
  modify() {
    this.processing = true;
    this.tasksService.update(
      this.task.id,
      this.editTaskForm.value
    ).pipe(
        finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.newTask.emit(result);
      });
  }

}
