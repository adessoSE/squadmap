import {Injectable} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {CreateEmployeeModel} from '../models/createEmployee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {


  public employees: EmployeeModel[] = [];

  constructor(public http: HttpClient) {}

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

  addEmployee(employee: CreateEmployeeModel) {
    return this.http.post('http://localhost:8080/employee/create', {
      firstName: employee.firstName,
      lastName: employee.lastName,
      birthday: employee.birthday,
      email: employee.email,
      phone: employee.phone,
      isExternal: employee.isExternal
    });
  }

  deleteEmployee(employeeId: number) {
    return this.http.delete('http://localhost:8080/employee/delete/' + employeeId);
  }

  updateEmployee(newEmployee: CreateEmployeeModel, employeeId: number) {
    return this.http.put('http://localhost:8080/employee/update/' + employeeId, {
      firstName: newEmployee.firstName,
      lastName: newEmployee.lastName,
      birthday: newEmployee.birthday,
      email: newEmployee.email,
      phone: newEmployee.phone,
      isExternal: newEmployee.isExternal
    });
  }
}
