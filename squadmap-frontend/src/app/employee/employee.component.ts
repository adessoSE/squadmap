import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {EmployeeService} from '../service/employee.service';
import {EmployeeModel} from '../models/employee.model';
import {CreateEmployeeModel} from '../models/createEmployee.model';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  public employees: EmployeeModel[];
  public searchText: string;

  constructor(private employeeService: EmployeeService, private router: Router) {
  }

  ngOnInit() {
    this.employees = this.employeeService.getEmployees();
  }

  onOpenEmployeeProfile(employee: EmployeeModel) {
    this.router.navigate(['/employee/' + employee.employeeId]);
  }

  onAddEmployee() {
    const employee = new CreateEmployeeModel('Test', 'Employee', new Date(), 'test@test.de', '+49', false);
    this.employeeService.addEmployee(employee).subscribe(res => {
      console.log(res);
      this.employees = this.employeeService.getEmployees();
    });
  }

  onDelete(employee: EmployeeModel) {
    this.employeeService.deleteEmployee(employee.employeeId).subscribe(res => {
      console.log(res);
      this.employees = this.employeeService.getEmployees();
    });
  }

  onUpdate(employee: EmployeeModel) {
    this.employeeService.updateEmployee(employee, employee.employeeId).subscribe(res => {
      console.log(res);
      this.employees = this.employeeService.getEmployees();
    });
  }
}
