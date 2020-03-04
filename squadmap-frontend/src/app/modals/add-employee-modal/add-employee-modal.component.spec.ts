import {async, TestBed} from '@angular/core/testing';
import {FilterEmployeesPipe} from '../../pipes/filterEmployees/filterEmployees.pipe';
import {BsModalRef, BsModalService, ModalModule} from 'ngx-bootstrap';
import {FormsModule} from '@angular/forms';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AddEmployeeModalComponent} from './add-employee-modal.component';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {of} from 'rxjs';

describe('Employee Modal Component', () => {
  let fixture;
  let component;
  let element;
  let de;
  let valueServiceSpy: jasmine.SpyObj<WorkingOnService>;
  let getDummyResponse;
  const dummyResponse = 'dummy';
  beforeEach(() => {
    const workingOnSpy = jasmine.createSpyObj('WorkingOnService', ['createWorkingOn']);
    getDummyResponse = workingOnSpy.createWorkingOn.and.returnValue( of(dummyResponse) );
    TestBed.configureTestingModule({
      declarations: [
        AddEmployeeModalComponent,
        FilterEmployeesPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [BsModalService,
        {provide: WorkingOnService, useValue: workingOnSpy},
        BsModalRef]
    });
    fixture = TestBed.createComponent(AddEmployeeModalComponent);
    component = fixture.componentInstance; // to access properties and methods
    element = fixture.nativeElement; // to access DOM element
    de = fixture.debugElement; // test helper
    valueServiceSpy = TestBed.get(WorkingOnService);
  });

  it('should create the modal', () =>  {
    expect(component).toBeTruthy();
  });

  it('should render the error message',  () => {
    component.handleError('error');
    expect((element.querySelector('.alert') as HTMLElement).innerText).toEqual('error');
  });
});
