import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap";
import {EmployeeService} from "../../services/employee/employee.service";
import {CreateEmployeeModel} from "../../models/createEmployee.model";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {merge, Subscription} from "rxjs";
import {filter} from "rxjs/operators";
import {emailValidator} from "../../validators/email-validator";
import {phoneNumberValidator} from "../../validators/phone-number-validator";
import {dateInPastValidator} from "../../validators/date-in-past-validator";

@Component({
  selector: 'app-create-employee-modal',
  templateUrl: './create-employee-modal.component.html',
  styleUrls: ['./create-employee-modal.component.css']
})
export class CreateEmployeeModalComponent implements OnInit, OnDestroy {


  public errorMessage: string;
  public errorOccurred: boolean;

  imageSeed: string;
  private lastGeneratedSeed: string;

  form: FormGroup;
  private sub: Subscription;

  constructor(public modalRef: BsModalRef,
              public employeeService: EmployeeService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.errorMessage = '';
    this.form = this.formBuilder.group({
      firstName: ['', [
        Validators.required,
        Validators.maxLength(50)
      ]],
      lastName: ['', [
        Validators.required,
        Validators.maxLength(50)
      ]],
      birthday: ['', [
        Validators.required,
        minimumDateValidator,
        dateInPastValidator
      ]],
      email: ['', [
        Validators.required,
        emailValidator
      ]],
      phone: ['', [
        Validators.required,
        phoneNumberValidator
      ]],
      imageType: ['initials', []],
      isExternal: [false, []]
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
    this.employeeService.addEmployee(employee).subscribe(() => {
      this.closeModal();
    }, error => {
      this.handleError(error.error.message);
    });
  }

  closeModal() {
    this.modalRef.hide();
    location.reload();
  }

  public handleError(message: string) {
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
