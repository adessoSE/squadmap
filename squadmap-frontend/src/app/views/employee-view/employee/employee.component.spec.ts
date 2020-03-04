import {TestBed} from '@angular/core/testing';
import {BsModalService, ModalModule} from 'ngx-bootstrap';
import {EmployeeComponent} from './employee.component';
import {FormsModule} from '@angular/forms';
import {FilterEmployeesPipe} from '../../../pipes/filterEmployees/filterEmployees.pipe';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeModel} from '../../../models/employee.model';

describe('EmployeeComponent', () => {
  let fixture;
  let component;
  let element;
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        EmployeeComponent,
        FilterEmployeesPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [BsModalService]
    });
    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance; // to access properties and methods
    element = fixture.nativeElement; // to access DOM element
  });

  it('should create the EmployeeComponent',  () => {
  expect(component).toBeDefined();
  });

  it('should create the search box', () => {
    expect(element.querySelector('#searchText')).toBeTruthy();
  });

  it('should create the table', () => {
    expect(element.querySelector('table')).toBeTruthy();
  });

  it('should add 1 row to table', () => {
     fixture.employees = [];
     fixture.employees.push(new EmployeeModel(1, 'test', 'test', new Date(), '', '', false, '', []));
     const table = element.querySelector('table');
     expect(table.rows.length).toBe(1);
  });
});
