import {AbstractControl} from '@angular/forms';

export class ConfirmPasswordChecker {
   matchPassword(abstractControl: AbstractControl, firstPassword: string,
                 secondPassword: string): {matchPassword: boolean} {
    const password = abstractControl.get(firstPassword).value;
    const confirmPassword = abstractControl.get(secondPassword).value;
    if (password !== confirmPassword) {
      abstractControl.get(secondPassword).setErrors({ passwordsNotMatch: true });
      return {matchPassword: true};
    } else {
      abstractControl.get(secondPassword).setErrors(null);
      return null;
    }
  }
}
