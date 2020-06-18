import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmployeeModel} from "../../models/employee.model";
import {BsModalRef} from "ngx-bootstrap";
import {EmployeeService} from "../../services/employee/employee.service";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {CreateEmployeeModel} from "../../models/createEmployee.model";
import {emailValidator} from "../../validators/email-validator";
import {phoneNumberValidator} from "../../validators/phone-number-validator";
import {dateInPastValidator} from "../../validators/date-in-past-validator";
import {filter} from "rxjs/operators";
import {merge, Subscription} from "rxjs";

@Component({
  selector: 'app-update-employee-modal',
  templateUrl: './update-employee-modal.component.html',
  styleUrls: ['./update-employee-modal.component.css']
})
export class UpdateEmployeeModalComponent implements OnInit, OnDestroy {

  public employee: EmployeeModel;

  errorMessage: string;
  errorOccurred: boolean;

  imageSeed: string;
  private lastGeneratedSeed: string;

  form: FormGroup;
  private sub: Subscription;

  constructor(private modalRef: BsModalRef,
              public employeeService: EmployeeService,
              private dateFormatterService: DateFormatterService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.errorMessage = '';

    const imageSplit = this.employee.image.split('/');
    this.lastGeneratedSeed = imageSplit.pop();
    let imageType = imageSplit.pop();
    if (!imageType || imageType === '' || imageType === 'initials') {
      imageType = 'initials';
      this.imageSeed = this.employee.firstName.charAt(0) + this.employee.lastName.charAt(0);
    }
    this.imageSeed = imageType + '/' + this.lastGeneratedSeed;

    this.form = this.formBuilder.group({
      firstName: [this.employee.firstName, [
        Validators.required,
        Validators.maxLength(50)
      ]],
      lastName: [this.employee.lastName, [
        Validators.required,
        Validators.maxLength(50)
      ]],
      birthday: [this.dateFormatterService.formatDate(this.employee.birthday), [
        Validators.required,
        minimumDateValidator,
        dateInPastValidator
      ]],
      email: [this.employee.email, [
        Validators.required,
        emailValidator
      ]],
      phone: [this.employee.phone, [
        Validators.required,
        phoneNumberValidator
      ]],
      imageType: [imageType, []],
      isExternal: [this.employee.isExternal, []]
    });

    this.sub = merge(
      this.form.get('firstName').valueChanges,
      this.form.get('lastName').valueChanges,
      this.form.get('imageType').valueChanges
    ).pipe(
      filter(() => this.form.get('firstName').valid),
      filter(() => this.form.get('lastName').valid))
      .subscribe(() => this.changeSeed());

  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const employee = new CreateEmployeeModel(
      this.form.value.firstName,
      this.form.value.lastName,
      this.form.value.birthday,
      this.form.value.email,
      this.form.value.phone,
      this.form.value.isExternal,
      this.imageSeed
    );
    this.employeeService.updateEmployee(employee, this.employee.employeeId).subscribe(() => {
      this.modalRef.hide();
      location.reload();
    }, error => {
      this.handleError(error.error.message);
    });
  }

  handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }

  generateRandomString(): string {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
  }

  randomizeImage(): void {
    this.lastGeneratedSeed = this.generateRandomString();
    this.changeSeed();
  }

  changeSeed() {
    if (this.form.get('imageType').value === '' || this.form.get('imageType').value === 'initials') {
      this.imageSeed = 'initials/' + this.form.get('firstName').value.charAt(0) + this.form.get('lastName').value.charAt(0);
    } else {
      this.imageSeed = this.form.get('imageType').value + '/' + this.lastGeneratedSeed;
    }
  }
}
