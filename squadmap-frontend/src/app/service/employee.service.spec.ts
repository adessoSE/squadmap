import {TestBed} from '@angular/core/testing';
import {EmployeeService} from './employee.service';
import {EmployeeModel} from '../models/employee.model';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {CreateEmployeeModel} from '../models/createEmployee.model';

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmployeeService]
    });
    service = TestBed.get(EmployeeService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterAll(() => {
    httpMock.verify();
  });

  it('should be created',  () => {
    const employeeService: EmployeeService = TestBed.get(EmployeeService);
    expect(employeeService).toBeTruthy();
  });

  it('should be able to retrieve all employees from the API via GET', () => {
    const dummyEmployees: EmployeeModel[] = [
      {
      employeeId: 1,
      firstName: 'Test1',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test1@name.de',
      phone: '0123456789',
      isExternal: false,
      projects: []
    },
      {
        employeeId: 2,
        firstName: 'Test2',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test2@name.de',
        phone: '0123456789',
        isExternal: true,
        projects: []
      },
    ];
    service.getEmployees().subscribe(() => {
      expect(service.employees.length).toBe(2);
      // expect(service.employees).toEqual(dummyEmployees);
      expect(JSON.stringify(service.employees)).toEqual(JSON.stringify(dummyEmployees));
    });
    const request = httpMock.expectOne('http://localhost:8080/employee/all');
    expect(request.request.method).toBe('GET');
    request.flush(dummyEmployees);
  });

  it('should be able to retrieve one specific employee from the API via GET', () => {
    const dummyEmployee: EmployeeModel = {
        employeeId: 1,
        firstName: 'Test1',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test1@name.de',
        phone: '0123456789',
        isExternal: false,
        projects: []
    };
    service.getEmployee(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyEmployee));
    });
    const request = httpMock.expectOne('http://localhost:8080/employee/1');
    expect(request.request.method).toBe('GET');
    request.flush(dummyEmployee);
  });

  it('should be able to add an employee through the API via POST', () => {
    const dummyEmployee: CreateEmployeeModel = {
      firstName: 'Test1',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test1@name.de',
      phone: '0123456789',
      isExternal: false
    };
    const dummyEmployeeAPI: EmployeeModel = {
        employeeId: 1,
        firstName: 'Test1',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test1@name.de',
        phone: '0123456789',
        isExternal: false,
        projects: []
      };
    service.addEmployee(dummyEmployee).subscribe(res => {
      expect(dummyEmployeeAPI.employeeId).toBe(1);
    });
    const request = httpMock.expectOne('http://localhost:8080/employee/create');
    expect(request.request.method).toBe('POST');
    request.flush(dummyEmployeeAPI);
  });

  it('should be able to delete an employee from the API via DELETE', () => {
    const dummyEmployees: EmployeeModel[] = [
      {
        employeeId: 1,
        firstName: 'Test1',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test1@name.de',
        phone: '0123456789',
        isExternal: false,
        projects: []
      },
      {
        employeeId: 2,
        firstName: 'Test2',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test2@name.de',
        phone: '0123456789',
        isExternal: true,
        projects: []
      },
    ];
    service.deleteEmployee(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyEmployees));
    });
    const request = httpMock.expectOne('http://localhost:8080/employee/delete/1');
    expect(request.request.method).toBe('DELETE');
    dummyEmployees.splice(0, 1);
    request.flush(dummyEmployees);
  });

  it('should be able to update an employee from the API via PUT', () => {
    const dummyEmployees: EmployeeModel[] = [
      {
        employeeId: 1,
        firstName: 'Test1',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test1@name.de',
        phone: '0123456789',
        isExternal: false,
        projects: []
      },
      {
        employeeId: 2,
        firstName: 'Test2',
        lastName: 'Name',
        birthday: new Date(),
        email: 'test2@name.de',
        phone: '0123456789',
        isExternal: true,
        projects: []
      },
    ];
    const newDummyEmployee: CreateEmployeeModel = {
      firstName: 'TestNew',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test1@name.de',
      phone: '0123456789',
      isExternal: false
    };
    service.updateEmployee(newDummyEmployee, 1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyEmployees));
    });
    const request = httpMock.expectOne('http://localhost:8080/employee/update/1');
    expect(request.request.method).toBe('PUT');
    dummyEmployees[0] = new EmployeeModel(
      1,
      newDummyEmployee.firstName,
      newDummyEmployee.lastName,
      newDummyEmployee.birthday,
      newDummyEmployee.email,
      newDummyEmployee.phone,
      newDummyEmployee.isExternal,
      []);
    request.flush(dummyEmployees);
  });
});
