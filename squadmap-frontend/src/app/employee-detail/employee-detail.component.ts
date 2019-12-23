import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {ActivatedRoute} from '@angular/router';
import {EmployeeService} from '../service/employee.service';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css']
})
export class EmployeeDetailComponent implements OnInit {

  private employee: EmployeeModel;

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService) { }

  ngOnInit() {
    this.employee = new EmployeeModel(0, '', '', new Date(), '', '', false, []);
    this.employeeService.getEmployee(this.route.snapshot.params.id).subscribe(res => {
      for (const project of res.projects) {
        project.since = new Date(project.since);
        project.until = new Date(project.until);
      }
      res.birthday = new Date(res.birthday);
      this.employee = new EmployeeModel(
        res.employeeId,
        res.firstName,
        res.lastName, res.birthday,
        res.email,
        res.phone,
        res.isExternal,
        res.projects );
    });
  }

}
