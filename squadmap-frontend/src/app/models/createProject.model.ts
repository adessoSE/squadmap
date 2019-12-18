

export class CreateProjectModel {
  constructor(
    public title: string,
    public description: string,
    public since: Date,
    public until: Date,
    public isExternal: boolean,
  ) {
  }
}
