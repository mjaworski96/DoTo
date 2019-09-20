import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../../utils/global-variables';
import {TasksService} from '../../../services/tasks.service';
import {TaskWithId} from "../../../../models/tasks";

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {
  @Input()
  projectId: number;

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
  }
  add() {
    this.tasksService.create(
      this.projectId,
      this.addTaskForm.value
    ).toPromise().then(result => {
      this.newTask.emit(result);
    });
  }

}
