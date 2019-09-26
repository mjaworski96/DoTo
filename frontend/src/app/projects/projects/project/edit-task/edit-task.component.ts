import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../../utils/global-variables';
import {TasksService} from '../../../services/tasks.service';
import {TaskWithId} from '../../../../models/task';

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

  addTaskForm: FormGroup;

  minShortDescriptionLength = GlobalVariables.minTaskShortDescriptionLength;
  maxShortDescriptionLength = GlobalVariables.maxTaskShortDescriptionLength;
  maxFullDescriptionLength = GlobalVariables.maxTaskFullDescriptionLength;

  @Output()
  newTask = new EventEmitter<TaskWithId>();

  constructor(private formBuilder: FormBuilder,
              private tasksService: TasksService) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.addTaskForm = this.formBuilder.group({
      shortDescription: ['', [
        Validators.required,
        Validators.minLength(this.minShortDescriptionLength),
        Validators.maxLength(this.maxShortDescriptionLength)
      ]],
      fullDescription: ['', [
        Validators.maxLength(this.maxFullDescriptionLength)
      ]],
    });
    this.setDefaultValues();
  }
  setDefaultValues(): void {
    if (this.task !== undefined) {
      this.addTaskForm.controls.shortDescription.setValue(this.task.shortDescription);
      this.addTaskForm.controls.fullDescription.setValue(this.task.fullDescription);
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
    this.tasksService.create(
      this.projectId,
      this.addTaskForm.value
    ).toPromise().then(result => {
      this.newTask.emit(result);
    });
  }
  modify() {
    this.tasksService.update(
      this.task.id,
      this.addTaskForm.value
    ).toPromise()
      .then(result => {
        this.newTask.emit(result);
      });
  }

}
