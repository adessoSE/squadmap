export class Employee {
  constructor(public firstName: string,
              public lastName: string,
              public birthday: Date,
              public email: string,
              public phone: string,
              public isExternal: boolean) {
  }

  testData() {
    this.firstName = 'Max';
    this.lastName = 'Mustermann';
    this.birthday = new Date('2019-12-10');
    this.email = 'MaxMustermann@MM.de';
    this.phone = '01263846165';
    this.isExternal = false;
  }
}
