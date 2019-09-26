import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ProjectWithId} from '../../models/project';

@Component({
  selector: 'app-modify-project-dialog',
  templateUrl: './modify-project-dialog.component.html',
  styleUrls: ['./modify-project-dialog.component.css']
})
export class ModifyProjectDialogComponent implements OnInit {

  @Input()
  project: ProjectWithId;

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }
  closeWindow(project: ProjectWithId) {
    this.activeModal.close(project);
  }
}
