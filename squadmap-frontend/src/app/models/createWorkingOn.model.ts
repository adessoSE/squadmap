export class CreateWorkingOnModel {
  constructor(
    public employeeId: number,
    public projectId: number,
    public since: Date,
    public until: Date
  ) {}
}
