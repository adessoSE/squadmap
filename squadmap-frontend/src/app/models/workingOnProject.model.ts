import {ProjectModel} from './project.model';

export class WorkingOnProjectModel {
  constructor(
    public workingOnId: number,
    public project: ProjectModel,
    public since: Date,
    public until: Date
  ) {}
}
