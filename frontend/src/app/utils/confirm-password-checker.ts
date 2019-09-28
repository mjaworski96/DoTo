import {AbstractControl} from '@angular/forms';

export class ConfirmPasswordChecker {
   matchPassword(abstractControl: AbstractControl, firstPassword: string,
                 secondPassword: string): {passwordsNotMatch: boolean} {
    const password = abstractControl.get(firstPassword).value;
    const confirmPassword = abstractControl.get(secondPassword).value;
    if (password !== confirmPassword) {
      return {passwordsNotMatch: true};
    } else {
      return null;
    }
  }
}
