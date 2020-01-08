import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CreateWorkingOnModel} from '../models/createWorkingOn.model';

@Injectable({
  providedIn: 'root'
})
export class WorkingOnService {
  constructor(private http: HttpClient) {}

  createWorkingOn(createWorkingOn: CreateWorkingOnModel) {
    return this.http.post('http://localhost:8080/workingOn/create', {
      employeeId: createWorkingOn.employeeId,
      projectId: createWorkingOn.projectId,
      since: createWorkingOn.since,
      until: createWorkingOn.until
    });
  }

  deleteWorkingOn(id: number) {
    return this.http.delete('http://localhost:8080/workingOn/delete/' + id);
  }

  updateWorkingOn(workingOnId: number, employeeId: number, projectId: number, since: Date, until: Date) {
    return this.http.put('http://localhost:8080/workingOn/update/' + workingOnId, {
      employeeId,
      projectId,
      since,
      until
    });
  }
}
