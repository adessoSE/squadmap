export class CreateEmployeeModel {
  constructor(
    public firstName: string,
    public lastName: string,
    public birthday: Date,
    public email: string,
    public phone: string,
    public isExternal: boolean,
    public image: string,
  ) {}
}
