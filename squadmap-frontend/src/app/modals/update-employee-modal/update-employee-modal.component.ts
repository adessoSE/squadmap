import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from "../../models/employee.model";
import {BsModalRef} from "ngx-bootstrap";
import {EmployeeService} from "../../services/employee/employee.service";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {CreateEmployeeModel} from "../../models/createEmployee.model";
import {emailValidator} from "../../validators/email-validator";
import {phoneNumberValidator} from "../../validators/phone-number-validator";

@Component({
  selector: 'app-update-employee-modal',
  templateUrl: './update-employee-modal.component.html',
  styleUrls: ['./update-employee-modal.component.css']
})
export class UpdateEmployeeModalComponent implements OnInit {

  public employee: EmployeeModel;

  errorMessage: string;
  errorOccurred: boolean;

  private imageType: string;
  imageSeed: string;

  form: FormGroup;

  constructor(private modalRef: BsModalRef,
              public employeeService: EmployeeService,
              private dateFormatterService: DateFormatterService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.errorMessage = '';

    const index = this.employee.image.indexOf('/');
    const imageSplit = [this.employee.image.slice(0, index), this.employee.image.slice(index + 1)];
    this.imageSeed = imageSplit.pop();
    this.imageType = imageSplit.pop();
    if (this.imageType === '' || this.imageType === 'initials') {
      this.imageType = 'initials';
      this.imageSeed = '';
    }
    this.imageSeed = this.imageType + '/' + this.imageSeed;

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
        minimumDateValidator
      ]],
      email: [this.employee.email, [
        Validators.required,
        emailValidator
      ]],
      phone: [this.employee.phone, [
        Validators.required,
        phoneNumberValidator
      ]],
      imageType: [this.imageType,[
      ]],
      isExternal: [this.employee.isExternal,[
      ]]
    });
  }

  onSubmit() {
    const employee = new CreateEmployeeModel(
      this.form.value.firstName,
      this.form.value.lastName,
      this.form.value.birthday,
      this.form.value.email,
      this.form.value.phone,
      this.form.value.isExternal,
      this.form.value.imageType + '/' + this.generateRandomString()
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

  changeSeed() {
    if(this.form.value.imageType === '' || this.form.value.imageType === 'initials'){
      this.imageSeed ='initials/' + this.form.value.firstName.charAt(0) + '_' + this.form.value.lastName.charAt(0);
    }else {
      this.imageSeed = this.form.value.imageType + '/' + this.generateRandomString();
    }
  }
}
