import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class WorkingOnService {
  constructor(private http: HttpClient) {}

  addEmployeeToProject(employee: EmployeeModel, project: ProjectModel) {
    return this.http.post('http://localhost:8080/workingOn/create', {
      employeeId: employee.employeeId,
      projectId: project.projectId,
      since: new Date(),
      until: new Date()
    });
  }

  removeEmployeeFromProject(id: number) {
    return this.http.delete('http://localhost:8080/workingOn/delete/' + id);
  }
}
