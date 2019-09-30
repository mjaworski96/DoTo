import {ConfirmPasswordChecker} from '../../../../../utils/confirm-password-checker';
import {AbstractControl} from '@angular/forms';

export class ChangePasswordConfirmPassword extends ConfirmPasswordChecker {
  matchPassword(abstractControl: AbstractControl): {passwordsNotMatch: boolean} {
    return super.matchPassword(abstractControl, 'newPassword',
      'confirmNewPassword');
  }
}
