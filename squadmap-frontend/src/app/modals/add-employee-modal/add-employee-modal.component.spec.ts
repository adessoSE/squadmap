import {TestBed} from '@angular/core/testing';
import {FilterEmployeesPipe} from '../../pipes/filterEmployees/filterEmployees.pipe';
import {BsModalRef, BsModalService, ModalModule} from 'ngx-bootstrap';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AddEmployeeModalComponent} from './add-employee-modal.component';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {EmployeeModel} from "../../models/employee.model";

describe('Add Employee Modal Component', () => {
  let fixture;
  let component;
  let workingOnService: WorkingOnService;
  let workinOnServiceSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        AddEmployeeModalComponent,
        FilterEmployeesPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        HttpClientTestingModule,
      ],
      providers: [BsModalService,
        WorkingOnService,
        BsModalRef]
    });

    fixture = TestBed.createComponent(AddEmployeeModalComponent);
    component = fixture.componentInstance; // to access properties and methods
    workingOnService = fixture.debugElement.injector.get(WorkingOnService);
    workinOnServiceSpy = spyOn(workingOnService, 'createWorkingOn').and.callThrough();
    fixture.detectChanges();
  });

  it('should create the modal', () =>  {
    fixture.detectChanges();
    expect(component.errorMessage).toEqual('');
    expect(component).toBeTruthy();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
    // TODO check error message on DOM Element
  });

  it('should call the createWorkingOn Method on call of onAddEmployee',  () => {
    const dummyEmployeeAPI: EmployeeModel = {
      employeeId: 1,
      firstName: 'Test1',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test3@name.de',
      phone: '0123456789',
      isExternal: false,
      image: '',
      projects: []
    };
    const date = new Date();
    component.onAddEmployee(dummyEmployeeAPI, date, date, 10);
    expect(workinOnServiceSpy).toHaveBeenCalled();
  });
});
