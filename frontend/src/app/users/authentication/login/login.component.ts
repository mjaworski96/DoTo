import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../services/authentication.service';
import {GlobalVariables} from '../../../utils/global-variables';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  minUsernameLength = GlobalVariables.minUsernameLength;
  maxUsernameLength = GlobalVariables.maxUsernameLength;
  minPasswordLength = GlobalVariables.minUsernameLength;
  maxPasswordLength = GlobalVariables.maxPasswordLength;
  processing = false;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.buildForm();
  }
  buildForm(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [
        Validators.required,
        Validators.minLength(this.minUsernameLength),
        Validators.maxLength(this.maxUsernameLength)
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength)
      ]],
    });
  }
  login(): void {
    this.processing = true;
    this.authenticationService.login(this.loginForm.value, this.loginFinalize, this);
  }
  loginFinalize(): void {
    this.processing = false;
  }

}
