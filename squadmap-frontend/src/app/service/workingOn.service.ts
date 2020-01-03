import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class WorkingOnService {
  constructor(private http: HttpClient) {}

  addEmployeeToProject(employeeId: number, projectId: number) {
    return this.http.post('http://localhost:8080/workingOn/create', {
      employeeId,
      projectId,
      since: new Date(),
      until: new Date()
    });
  }

  addProjectToEmployee(project: ProjectModel, employee: EmployeeModel) {
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

  update(workingOnId: number, employeeId: number, projectId: number, since: Date, until: Date) {
    return this.http.put('http://localhost:8080/workingOn/update/' + workingOnId, {
      employeeId,
      projectId,
      since,
      until
    });
  }
}
