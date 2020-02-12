import {Injectable} from '@angular/core';
import {EmployeeModel} from '../../models/employee.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {CreateEmployeeModel} from '../../models/createEmployee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {


  public employees: EmployeeModel[] = [];

  constructor(private http: HttpClient) {}

  getEmployees() {
    this.employees = [];
    return this.http.get<EmployeeModel[]>('http://localhost:8080/employee/all').pipe(map( res => {
      Object.values(res).map(receivedData => {
          for (const project of receivedData.projects) {
            project.since = new Date(project.since);
            project.until = new Date(project.until);
          }
          this.employees.push(new EmployeeModel(
            receivedData.employeeId,
            receivedData.firstName,
            receivedData.lastName,
            receivedData.birthday,
            receivedData.email,
            receivedData.phone,
            receivedData.isExternal,
            receivedData.image,
            receivedData.projects
          ));
        });
        }
      ));
  }

  getEmployee(id: number) {
    return this.http.get<EmployeeModel>('http://localhost:8080/employee/' + id).pipe(map(res => {
      res.birthday = new Date(res.birthday);
      for (const project of res.projects) {
        project.since = new Date(project.since);
        project.until = new Date(project.until);
      }
      return res;
    }));
  }

  addEmployee(employee: CreateEmployeeModel) {
    return this.http.post<number>('http://localhost:8080/employee/create', {
      firstName: employee.firstName,
      lastName: employee.lastName,
      birthday: employee.birthday,
      email: employee.email,
      phone: employee.phone,
      isExternal: employee.isExternal,
      image: employee.image
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
      isExternal: newEmployee.isExternal,
      image: newEmployee.image
    });
  }

}
