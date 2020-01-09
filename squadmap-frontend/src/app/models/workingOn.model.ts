import {ProjectModel} from './project.model';
import {EmployeeModel} from './employee.model';

export class WorkingOnModel {
  constructor(
    public workingOnId: number,
    public employee: EmployeeModel,
    public project: ProjectModel,
    public since: Date,
    public until: Date
  ) {}
}
