import {EmployeeModel} from './employee.model';

export class WorkingOnEmployeeModel {
  constructor(
    public workingOnId: number,
    public employee: EmployeeModel,
    public since: Date,
    public until: Date,
    public workload: number,
  ) {}
}
