export class WorkingOnModel {
  constructor(
    public workingOnId: number,
    public project,
    public since: Date,
    public until: Date
  ) {}
}
