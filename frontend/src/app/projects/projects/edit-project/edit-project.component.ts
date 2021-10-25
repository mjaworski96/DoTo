import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../utils/global-variables';
import {SessionStorageService} from '../../../shared/services/session-storage.service';
import {ProjectsService} from '../../services/projects.service';
import {Router} from '@angular/router';
import {ProjectWithId} from "../../../models/project";
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-add-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {
  @Input()
  project: ProjectWithId;

  @Output()
  newProject = new EventEmitter<ProjectWithId>();

  editProjectForm: FormGroup;

  maxNameLength = GlobalVariables.maxProjectNameLength;
  maxDescriptionLength = GlobalVariables.maxProjectDescriptionLength;
  textAreaRows = GlobalVariables.textAreaRows;
  
  processing = false;

  constructor(private formBuilder: FormBuilder,
              private sessionStorageService: SessionStorageService,
              private projectsService: ProjectsService,
              private router: Router) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.editProjectForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.maxLength(this.maxNameLength)
      ]],
      description: ['', [
        Validators.maxLength(this.maxDescriptionLength)
      ]],
    });
    this.setValuesForModification();
  }
  setValuesForModification(): void {
    if (this.project !== undefined) {
      this.editProjectForm.controls.name.setValue(this.project.name);
      this.editProjectForm.controls.description.setValue(this.project.description);
    }
  }
  edit() {
    if (this.project === undefined) {
      this.add();
    } else {
      this.modify();
    }
  }
  add() {
    this.processing = true;
    const userId = this.sessionStorageService.getUserId();
    this.projectsService.create(
      userId,
      this.editProjectForm.value
    ).pipe(
        finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.editProjectForm.reset();
        this.router.navigate(['projects', result.id]);
      });
  }
  modify() {
    this.processing = true;
    this.projectsService.update(
      this.project.id,
      this.editProjectForm.value
    ).pipe(
        finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.newProject.emit(result);
      });
  }
}
