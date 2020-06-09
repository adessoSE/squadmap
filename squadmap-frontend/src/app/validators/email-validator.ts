import {AbstractControl} from '@angular/forms';

export function emailValidator(control: AbstractControl): { [key: string]: any } | null {
  const email: string = control.value;
  console.log(email);
  const validEmailRegex = /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>().,;\s@"]+\.?)+([^<>().,;:\s@"]{2,}|[\d.]+))$/;
  if (!validEmailRegex.test(email)) {
    return {emailInvalid: true};
  } else {
    return null;
  }
}
