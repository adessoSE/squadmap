import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';
import {WorkingOnModel} from '../../models/workingOn.model';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WorkingOnService {
  constructor(private http: HttpClient) {}

  createWorkingOn(createWorkingOn: CreateWorkingOnModel) {
    return this.http.post(environment.base_api_url + '/workingOn/create', {
      employeeId: createWorkingOn.employeeId,
      projectId: createWorkingOn.projectId,
      since: createWorkingOn.since,
      until: createWorkingOn.until,
      workload: createWorkingOn.workload
    });
  }

  deleteWorkingOn(id: number) {
    return this.http.delete(environment.base_api_url + '/workingOn/delete/' + id);
  }

  updateWorkingOn(workingOnId: number, employeeId: number, projectId: number, since: Date, until: Date, workload: number) {
    return this.http.put(environment.base_api_url + '/workingOn/update/' + workingOnId, {
      employeeId,
      projectId,
      since,
      until,
      workload
    });
  }

  getWorkingOn(workingOnId: number) {
    return this.http.get<WorkingOnModel>(environment.base_api_url + '/workingOn/' + workingOnId);
  }
}
