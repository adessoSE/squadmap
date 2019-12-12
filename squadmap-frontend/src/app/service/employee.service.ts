import {Injectable} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {


  public employees: EmployeeModel[] = [];

  constructor(public http: HttpClient) {}

  getCurrentEmployeeList() {
    return this.employees;
  }

  getEmployees() {
    this.employees = [];
    this.http.get<EmployeeModel[]>('http://localhost:8080/employee/all').pipe(map( res => {
      Object.values(res).map(recievedData => {
          this.employees.push(new EmployeeModel(
            recievedData.employeeId,
            recievedData.firstName,
            recievedData.lastName,
            recievedData.birthday,
            recievedData.email,
            recievedData.phone,
            recievedData.isExternal,
            recievedData.projects
          ));
        });
        }
      ))
        .subscribe(() => {
          return this.employees;
        }
      );
    return this.employees;
  }

  getEmployee(id: number): EmployeeModel {
    let getEmployee: EmployeeModel;
    this.employees.filter(employee => {
      if (employee.employeeId === id) {
        getEmployee = employee;
      }
    });
    return getEmployee;
  }
}
