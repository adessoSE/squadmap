import {FilterEmployeesPipe, FilterSettings} from './filterEmployees.pipe';
import {EmployeeModel} from '../../models/employee.model';

describe('FilterEmployeePipe', () => {
  let pipe: FilterEmployeesPipe;
  const date: Date = new Date();
  let dummyEmployees: EmployeeModel[];
  let filterSettings: FilterSettings = {
    searchText: '',
  };

  beforeEach(() => {
    pipe = new FilterEmployeesPipe();
    dummyEmployees = [
      {
        employeeId: 1,
        firstName: 'Kevin',
        lastName: 'Name',
        birthday: date,
        email: 'test1@name.de',
        phone: '0123456789',
        isExternal: false,
        projects: []
      },
      {
        employeeId: 2,
        firstName: 'Bert',
        lastName: 'Name',
        birthday: date,
        email: 'test2@name.de',
        phone: '0123456789',
        isExternal: true,
        projects: []
      },
    ];
    filterSettings = {
      searchText: '',
    };
  });

  it('should create an instance', () =>  {
    expect(pipe).toBeTruthy();
  });

  describe('Correct Inputs', () => {

    it('should filter employeeList only with a search text', () =>  {
      filterSettings.searchText = 'bert';
      const expectedEmployees: EmployeeModel[] = [
        {
          employeeId: 2,
          firstName: 'Bert',
          lastName: 'Name',
          birthday: date,
          email: 'test2@name.de',
          phone: '0123456789',
          isExternal: true,
          projects: []
        },
      ];
      expect(JSON.stringify(pipe.transform(dummyEmployees, filterSettings))).toEqual(JSON.stringify(expectedEmployees));
    });

    it('should filter employeeList with search text and hideExternal', () => {
      filterSettings.searchText = 'ber';
      filterSettings.hideExternal = true;
      const expectedEmployees: EmployeeModel[] = [];
      expect(JSON.stringify(pipe.transform(dummyEmployees, filterSettings))).toEqual(JSON.stringify(expectedEmployees));
    });

    it('should show only not external Employees',  () => {
      filterSettings.hideExternal = true;
      const expectedEmployees: EmployeeModel[] = [
        {
          employeeId: 1,
          firstName: 'Kevin',
          lastName: 'Name',
          birthday: date,
          email: 'test1@name.de',
          phone: '0123456789',
          isExternal: false,
          projects: []
        },
      ];
      expect(JSON.stringify(pipe.transform(dummyEmployees, filterSettings))).toEqual(JSON.stringify(expectedEmployees));
    });
  });

  describe('Bad Inputs', () => {
    it('should not filter', () => {
      expect(JSON.stringify(pipe.transform(dummyEmployees, filterSettings))).toEqual(JSON.stringify(dummyEmployees));
    });
  });
});
