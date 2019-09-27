import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../utils/global-variables';
import {SessionStorageService} from '../../../shared/services/session-storage.service';
import {ProjectsService} from '../../services/projects.service';
import {Router} from '@angular/router';
import {ProjectWithId} from "../../../models/project";

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

  minNameLength = GlobalVariables.minProjectNameLength;
  maxNameLength = GlobalVariables.maxProjectNameLength;
  maxDescriptionLength = GlobalVariables.maxProjectDescriptionLength;

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
        Validators.minLength(this.minNameLength),
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
    const username = this.sessionStorageService.getUsername();
    this.projectsService.create(
      username,
      this.editProjectForm.value
    ).toPromise()
      .then(result => {
        this.editProjectForm.reset();
        this.router.navigate(['projects', result.id]);
      });
  }
  modify() {
    this.projectsService.update(
      this.project.id,
      this.editProjectForm.value
    ).toPromise()
      .then(result => {
        this.newProject.emit(result);
      });
  }
}
