import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';
import {WorkingOnModel} from '../../models/workingOn.model';

@Injectable({
  providedIn: 'root'
})
export class WorkingOnService {
  constructor(private http: HttpClient) {}

  createWorkingOn(createWorkingOn: CreateWorkingOnModel) {
    return this.http.post('http://localhost:8080/api/workingOn/create', {
      employeeId: createWorkingOn.employeeId,
      projectId: createWorkingOn.projectId,
      since: createWorkingOn.since,
      until: createWorkingOn.until,
      workload: createWorkingOn.workload
    });
  }

  deleteWorkingOn(id: number) {
    return this.http.delete('http://localhost:8080/api/workingOn/delete/' + id);
  }

  updateWorkingOn(workingOnId: number, employeeId: number, projectId: number, since: Date, until: Date, workload: number) {
    return this.http.put('http://localhost:8080/api/workingOn/update/' + workingOnId, {
      employeeId,
      projectId,
      since,
      until,
      workload
    });
  }

  getWorkingOn(workingOnId: number) {
    return this.http.get<WorkingOnModel>('http://localhost:8080/api/workingOn/' + workingOnId);
  }
}
