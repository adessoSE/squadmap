import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap";
import {EmployeeService} from "../../services/employee/employee.service";
import {CreateEmployeeModel} from "../../models/createEmployee.model";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {Subscription} from "rxjs";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-create-employee-modal',
  templateUrl: './create-employee-modal.component.html',
  styleUrls: ['./create-employee-modal.component.css']
})
export class CreateEmployeeModalComponent implements OnInit,OnDestroy {


  public errorMessage: string;
  public errorOccurred: boolean;

  private imageType: string;
  imageSeed: string;

  form: FormGroup;
  private sub: Subscription;

  constructor(public modalRef: BsModalRef,
              public employeeService: EmployeeService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.errorMessage = '';
    this.form = this.formBuilder.group({
      firstName: ['',[
        Validators.required
      ]],
      lastName: ['',[
        Validators.required
      ]],
      birthday: ['',[
        Validators.required,
        minimumDateValidator
      ]],
      email: ['',[
        Validators.required,
      ]],
      phone: ['',[
        Validators.required,
      ]],
      imageType: ['initials',[
      ]],
      isExternal: [false,[
      ]]
    });

    this.sub = this.form.statusChanges
      .pipe(
        filter(() => this.form.valid))
      .subscribe(() => this.changeSeed());
  }

  ngOnDestroy(): void {
   this.sub.unsubscribe();
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

  changeSeed() {
    if(this.form.value.imageType === '' || this.form.value.imageType === 'initials'){
      this.imageSeed ='initials/' + this.form.value.firstName.charAt(0) + '_' + this.form.value.lastName.charAt(0);
    }else {
      this.imageSeed = this.form.value.imageType + '/' + this.generateRandomString();
    }
  }
}
