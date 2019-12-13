import {WorkingOnModel} from './workingOn.model';

export class EmployeeModel {
  constructor(
    public employeeId: number,
    public firstName: string,
    public lastName: string,
    public birthday: Date,
    public email: string,
    public phone: string,
    public isExternal: boolean,
    public projects: WorkingOnModel[]
  ) {
  }
}
