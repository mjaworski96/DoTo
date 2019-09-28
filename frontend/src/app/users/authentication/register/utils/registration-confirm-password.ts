import {AbstractControl} from '@angular/forms';
import {ConfirmPasswordChecker} from '../../../../utils/confirm-password-checker';

export class RegistrationConfirmPassword extends ConfirmPasswordChecker {
  matchPassword(abstractControl: AbstractControl): {passwordsNotMatch: boolean} {
    return super.matchPassword(abstractControl, 'password',
      'confirmPassword');
  }
}
