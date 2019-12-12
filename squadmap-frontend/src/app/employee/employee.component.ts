import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {EmployeeService} from '../service/employee.service';
import {EmployeeModel} from '../models/employee.model';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  public employees: EmployeeModel[];
  public searchText: string;

  constructor(private employeeService: EmployeeService, private router: Router) { }

  ngOnInit() {
    this.employees = this.employeeService.getEmployees();
  }

  onOpenEmployeeProfile(employee: EmployeeModel) {
    this.router.navigate(['/employee/' + employee.employeeId]);
  }

}
