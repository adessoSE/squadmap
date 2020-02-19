import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {CreateEmployeeModel} from '../../models/createEmployee.model';
import {EmployeeService} from '../../services/employee/employee.service';
import {EmployeeModel} from '../../models/employee.model';
import {DateFormatterService} from '../../services/dateFormatter/dateFormatter.service';

@Component({
  selector: 'app-employee-modal',
  templateUrl: './employee-modal.component.html',
  styleUrls: ['./employee-modal.component.css']
})
export class EmployeeModalComponent implements OnInit {
  private header: string;
  private employee: EmployeeModel;
  private isNew: boolean;                       // variable that helps to call the correct corresponding method add/update Employee
  private birthday: string;
  private errorMessage: string;
  private errorOccurred: boolean;
  private imageType: string;
  private imageSeed: string;

  constructor(private modalRef: BsModalRef,
              private employeeService: EmployeeService,
              private dateFormatterService: DateFormatterService) { }

  ngOnInit() {
    this.errorMessage = '';
    if (!this.employee) {
      this.employee = new EmployeeModel(0, '', '', new Date(), '',  '', false, '', []);
      this.isNew = true;
      this.imageType = 'initials';
      this.imageSeed = '';
    } else {
      this.isNew = false;
      const index = this.employee.image.indexOf('/');
      const imageSplit = [this.employee.image.slice(0, index), this.employee.image.slice(index + 1)];
      this.imageSeed = imageSplit.pop();
      this.imageType = imageSplit.pop();
    }
    this.birthday = this.dateFormatterService.formatDate(this.employee.birthday);
  }

  onCreateEmployee(employeeForm: NgForm) {
    const pattern = /^[A-Za-z0-9]+$/;
    if (this.imageType === '') {
      this.imageType = 'initials';
      this.imageSeed = employeeForm.value.firstName.charAt(0) + employeeForm.value.lastName.charAt(0);
    } else if (!pattern.test(this.imageSeed)) {
      this.handleError('Only the characters A-Z,a-z and 0-9 are allowed');
      return;
    }
    const employee = new CreateEmployeeModel(
      employeeForm.value.firstName,
      employeeForm.value.lastName,
      employeeForm.value.birthday,
      employeeForm.value.email,
      employeeForm.value.phone,
      employeeForm.value.isExternal,
      this.imageType + '/' + this.imageSeed
    );
    if (this.isNew) {
      this.employeeService.addEmployee(employee).subscribe(() => {
        this.closeModal();
      }, error => {
        this.handleError(error.error.message);
      });
    } else {
      this.employeeService.updateEmployee(employee, this.employee.employeeId).subscribe(() => {
        this.closeModal();
      }, error => {
        this.handleError(error.error.message);
      });
    }
  }

  private closeModal() {
      this.modalRef.hide();
      location.reload();
  }

  private handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }
}
