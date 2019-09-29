import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ChangePasswordService} from '../../services/change-password.service';
import {SessionStorageService} from '../../../../shared/services/session-storage.service';
import {ChangePasswordConfirmPassword} from './utils/change-password-confirm-password';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {

  @ViewChild('passwordChangedMsg', {static: false})
  translatedMessage: ElementRef;

  passwordChangeForm: FormGroup;
  confirmPassword = new ChangePasswordConfirmPassword();
  minPasswordLength = 3;
  maxPasswordLength = 20;

  constructor(private formBuilder: FormBuilder,
              private passwordChangeService: ChangePasswordService,
              private sessionStorageService: SessionStorageService,
              private toastr: ToastrService) { }

  ngOnInit(): void {
    this.buildForm();
  }
  buildForm(): void {
    this.passwordChangeForm = this.formBuilder.group({
      oldPassword: ['', [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength)
      ]],
      newPassword: ['', [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength)
      ]],
      confirmNewPassword: ['', [Validators.required]],
    }, {
      validator: this.confirmPassword.matchPassword
    } );
  }
  changePassword(): void {
    this.passwordChangeService.changePassword(
      this.sessionStorageService.getUser().username,
      this.passwordChangeForm.value
    ).toPromise().then((result) => {
      if (result !== undefined) {
        this.toastr.info(this.translatedMessage.nativeElement.innerHTML, '', {
          timeOut: 5000,
          closeButton: true
        });
      }
    });
  }

}
