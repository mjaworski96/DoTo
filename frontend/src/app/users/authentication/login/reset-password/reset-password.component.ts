import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PasswordService} from '../../services/password.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  resetPasswordButtonPressed = false;
  sent = false;
  passwordResetForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private passwordService: PasswordService) { }

  ngOnInit(): void {
    this.buildForm();
  }
  buildForm(): void {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', [Validators.required]]
    });
  }

  resetPassword(): void {
    this.resetPasswordButtonPressed = true;
    this.passwordService.resetPassword(
      this.passwordResetForm.value
    ).toPromise().then(() => {
      this.sent = true;
    });
  }
}
