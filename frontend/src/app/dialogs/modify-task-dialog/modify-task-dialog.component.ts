import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TaskWithId} from '../../models/task';

@Component({
  selector: 'app-modify-task-dialog',
  templateUrl: './modify-task-dialog.component.html',
  styleUrls: ['./modify-task-dialog.component.css']
})
export class ModifyTaskDialogComponent implements OnInit {

  @Input()
  task: TaskWithId;

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  closeWindow(task: TaskWithId) {
    this.activeModal.close(task);
  }
}
