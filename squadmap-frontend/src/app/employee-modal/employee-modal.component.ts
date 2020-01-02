import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {CreateEmployeeModel} from '../models/createEmployee.model';
import {EmployeeService} from '../service/employee.service';
import {EmployeeModel} from '../models/employee.model';

@Component({
  selector: 'app-employee-modal',
  templateUrl: './employee-modal.component.html',
  styleUrls: ['./employee-modal.component.css']
})
export class EmployeeModalComponent implements OnInit {
  actionName: string;
  employee: EmployeeModel;
  isNew: boolean;


  constructor(private modalRef: BsModalRef,
              private employeeService: EmployeeService) { }

  ngOnInit() {
    if (!this.employee) {
      this.employee = new EmployeeModel(0, '', '', new Date(), '',  '', false, []);
      this.isNew = true;
    } else {
      this.isNew = false;
    }
  }

  onCreateEmployee(employeeForm: NgForm) {
    const employee = new CreateEmployeeModel(
      employeeForm.value.firstName,
      employeeForm.value.lastName,
      employeeForm.value.birthday,
      employeeForm.value.email,
      employeeForm.value.phone,
      employeeForm.value.isExternal
    );
    if (this.isNew) {
      this.employeeService.addEmployee(employee).subscribe(res => {console.log(res); });
    } else {
      this.employeeService.updateEmployee(employee, this.employee.employeeId).subscribe(res => {console.log(res); });
    }
    this.modalRef.hide();
    location.reload();
  }
}
