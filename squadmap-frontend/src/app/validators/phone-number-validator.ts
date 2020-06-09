import {AbstractControl} from '@angular/forms';

export function phoneNumberValidator(control: AbstractControl): { [key: string]: any } | null {
  const phoneNumber: string = control.value;
  const validPhoneNumberRegex = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/;
  if (!validPhoneNumberRegex.test(phoneNumber)) {
    return {phoneNumberInvalid: true};
  } else {
    return null;
  }
}
