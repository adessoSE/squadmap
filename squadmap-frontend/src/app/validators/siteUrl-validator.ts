import {AbstractControl} from '@angular/forms';

export function siteUrlValidator(control: AbstractControl): { [key: string]: any } | null {
  const input: string = control.value;
  const validUrlRegex = /^(?![^\n]*\.$)(?:https?:\/\/)?(?:(?:[2][1-4]\d|25[1-5]|1\d{2}|[1-9]\d|[1-9])(?:\.(?:[2][1-4]\d|25[1-5]|1\d{2}|[1-9]\d|[0-9])){3}(?::\d{4})?|[a-z\-]+(?:\.[a-z\-]+){2,})$/;
  if (input === undefined || input.trim() === '') {
    return null;
  }
  const urls: string[] = input.split(',');
  for (const url of urls) {
    if (url.trim() !== '' && !validUrlRegex.test(url.trim())) {
      return {urlsInvalid: true};
    }
  }
  return null;
}
