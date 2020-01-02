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
  public hideExternal: boolean;

  constructor(private employeeService: EmployeeService, private router: Router) {
  }

  ngOnInit() {
    this.hideExternal = false;
    this.searchText = '';
    this.employeeService.getEmployees().subscribe(() => {
      this.employees = this.employeeService.employees;
    });
  }

  onOpenEmployeeProfile(employee: EmployeeModel) {
    this.router.navigate(['/employee/' + employee.employeeId]);
  }

  onAddEmployee() {
    const employee = new CreateEmployeeModel('Test', 'Employee',
      new Date('December 17, 2017 15:00:00'), 'test@test.de', '0162123123', true);
    this.employeeService.addEmployee(employee).subscribe(res => {
      this.employeeService.getEmployee(+res).subscribe(emp => {
        this.employees.push(emp);
      });
    });
  }

  onDelete(employee: EmployeeModel) {
    this.employeeService.deleteEmployee(employee.employeeId).subscribe(() => {
      this.employeeService.getEmployees().subscribe(() => {
        this.employees = this.employeeService.employees;
      });
    });
  }

  onUpdate(employee: EmployeeModel) {
    this.employeeService.updateEmployee(employee, employee.employeeId).subscribe(res => {
      console.log(res);
      this.employeeService.getEmployees().subscribe(() => {
        this.employees = this.employeeService.employees;
      });
    });
  }

  onEdit(employee: EmployeeModel) {
    // TODO open here create Emoployee componenent
  }
}
