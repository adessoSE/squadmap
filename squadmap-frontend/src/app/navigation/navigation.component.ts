import {Component, OnInit, TemplateRef} from '@angular/core';
import {BsDropdownConfig, BsModalRef, BsModalService} from 'ngx-bootstrap';
import {FormBuilder, FormGroup, NgForm} from '@angular/forms';
import {EmployeeService} from '../service/employee.service';
import {CreateEmployeeModel} from '../models/createEmployee.model';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [{provide: BsDropdownConfig, useValue: {isAnimated: true, autoClose: true}}]
})
export class NavigationComponent implements OnInit {

  isCollapsed = true;
  modalRef: BsModalRef;
  employeeForm: FormGroup;

  constructor(private modalService: BsModalService,
              private formbuilder: FormBuilder,
              private employeeService: EmployeeService) { this.createEmployeeForm(); }

  private createEmployeeForm() {
    this.employeeForm = this.formbuilder.group({
        title: '',
        description: '',
        since: new Date(),
        until: new Date(),
        isExternal: false,
      }
    );
  }

  addEmployeeModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  addProjectModal(template: TemplateRef<any>) {
  }

  ngOnInit() {
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
    this.employeeService.addEmployee(employee).subscribe(
      res => {
        console.log(res);
      }
    );
    this.modalRef.hide();
    location.reload();
  }
}
