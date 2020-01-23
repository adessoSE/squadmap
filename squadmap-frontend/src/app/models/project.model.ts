import {WorkingOnEmployeeModel} from './workingOnEmployee.model';

export class ProjectModel {
  constructor(
    public projectId: number,
    public title: string,
    public description: string,
    public since: Date,
    public until: Date,
    public isExternal: boolean,
    public sites: string[],
    public employees: WorkingOnEmployeeModel[]
  ) {
  }
}
