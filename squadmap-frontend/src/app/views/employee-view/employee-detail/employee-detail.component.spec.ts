import {TestBed} from '@angular/core/testing';
import {EmployeeComponent} from '../employee/employee.component';
import {FilterEmployeesPipe} from '../../../pipes/filterEmployees/filterEmployees.pipe';
import {BsModalService, ModalModule} from 'ngx-bootstrap';
import {FormsModule} from '@angular/forms';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('EmployeeDetailComponent', () => {
  let fixture;
  let component;
  let element;
  let de;
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
    de = fixture.debugElement; // test helper
  });

  it('should create the EmployeeDetailComponent',  () => {
    expect(component).toBeDefined();
  });
});
