import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {ActivatedRoute, Params} from '@angular/router';
import {EmployeeService} from '../service/employee.service';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css']
})
export class EmployeeDetailComponent implements OnInit {

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService, private employee: EmployeeModel) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.employee = this.employeeService.getEmployee(+params.id);
    });
  }

}
