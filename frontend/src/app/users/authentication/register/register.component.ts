import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegistrationConfirmPassword} from './utils/registration-confirm-password';
import {AuthenticationService} from '../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  confirmPassword = new RegistrationConfirmPassword();

  minUsernameLength = 3;
  maxUsernameLength = 20;
  minPasswordLength = 3;
  maxPasswordLength = 20;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.buildForm();
  }
  buildForm(): void {
    this.registerForm = this.formBuilder.group({
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
      confirmPassword: ['', [Validators.required]],
      email: ['', [Validators.required]],
    }, {
      validator: this.confirmPassword.matchPassword
    } );
  }
  register(): void {
    this.authenticationService.register(this.registerForm.value);
  }

}
