import { Component, OnInit } from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {EmployeeService} from '../service/employee.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  private employeeList: EmployeeModel[] = [];
  public searchText: string;


  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {
    this.employeeList = this.employeeService.getEmployees();
  }

}
