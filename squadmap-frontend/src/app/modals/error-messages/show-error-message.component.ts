import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Component({
  selector: 'app-show-error-message',
  templateUrl: './show-error-message.component.html',
  styleUrls: ['./show-error-message.component.css']
})
export class ShowErrorMessageComponent implements OnInit {

  @Input() control: AbstractControl;

  private ERROR_MESSAGES = {
    required: () => 'This field is required.',
    minlength: (par) => `Min ${par.requiredLength} chars are required.`,
    maxlength: (par) => `Max ${par.requiredLength} chars are allowed.`,
    min: (par) => `The minimal value is ${par.min}`,
    max: (par) => `The maximal value is ${par.max}`,
    emailInvalid: () => 'This is not a valid email.',
    phoneNumberInvalid: () => 'This is not a valid phone number.',
    dateInPast: () => 'The date should be in past.',
    urlsInvalid: () => 'The URLs have an invalid form.',
    minimumDate: () => 'The date should be after 31.12.1899 and before 01.01.2501.'
  };

  constructor() {
  }

  ngOnInit(): void {
  }

  shouldShowErrors(): boolean {
    return this.control && this.control.errors && this.control.touched;
  }

  listOfErrors(): string[] {
    return Object.keys(this.control.errors).map(key =>
      this.ERROR_MESSAGES[key](this.control.getError(key)));
  }
}
