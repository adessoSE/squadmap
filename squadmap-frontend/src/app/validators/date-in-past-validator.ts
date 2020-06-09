import {AbstractControl} from '@angular/forms';

export function dateInPastValidator(control: AbstractControl): { [key: string]: any } | null {
  const date: Date = new Date(control.value);
  if (date.getTime() >= Date.now()) {
    return {dateInPast: true};
  } else {
    return null;
  }
}
