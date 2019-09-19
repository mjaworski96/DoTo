import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../utils/global-variables';
import {SessionStorageService} from '../../../shared/services/session-storage.service';
import {ProjectsService} from '../../services/projects.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {
  addProjectForm: FormGroup;

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
    this.addProjectForm = this.formBuilder.group({
      name: ['', [
        Validators.required,
        Validators.minLength(this.minNameLength),
        Validators.maxLength(this.maxNameLength)
      ]],
      description: ['', [
        Validators.maxLength(this.maxDescriptionLength)
      ]],
    });
  }
  add() {
    const username = this.sessionStorageService.getUsername();
    this.projectsService.create(
      username,
      this.addProjectForm.value
    ).toPromise()
      .then(result => {
        //this.router.navigated = false;
        this.router.navigate(['projects']);
      });
  }
}
