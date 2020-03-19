import {TestBed} from "@angular/core/testing";
import {BsModalRef, ModalModule} from "ngx-bootstrap";
import {FormsModule} from "@angular/forms";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {EmployeeModalComponent} from "./employee-modal.component";
import {FilterProjectsPipe} from "../../pipes/filterProjects/filterProjects.pipe";
import {IconsModule} from "../../icons/icons.module";
import {EmployeeService} from "../../services/employee/employee.service";
import {Observable} from "rxjs";

describe('Employee Modal', () => {
  let fixture;
  let component;
  let employeeService;
  let addEmployeeServiceSpy;
  let updateEmployeeServiceSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        EmployeeModalComponent,
        FilterProjectsPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        HttpClientTestingModule,
        IconsModule
      ],
      providers: [BsModalRef,
        EmployeeService,
        DateFormatterService
        ],
    });
    fixture = TestBed.createComponent(EmployeeModalComponent);
    component = fixture.componentInstance;
    employeeService = fixture.debugElement.injector.get(EmployeeService);
    addEmployeeServiceSpy = spyOn(employeeService, 'addEmployee').and.returnValue(new Observable());
    updateEmployeeServiceSpy = spyOn(employeeService, 'updateEmployee').and.returnValue(new Observable());
  });

  it('should create the modal', () =>  {
    fixture.detectChanges();
    expect(component.errorMessage).toEqual('');
    expect(component).toBeTruthy();
    expect(component.employee).toBeDefined();
    // TODO imageSeed etc.
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
    // TODO check error message on DOM Element
  });

  it('should generate a random string of length 6', () => {
    let response: string = component.generateRandomString(6);
    expect(response.length).toBe(6);
  });

  it('should clear the image seed',  () => {
    component.imageType = 'initials';
    component.clearSeed();
    expect(component.imageSeed).toBe('');
  });

  it('should call addEmployee when its new',  () => {
    fixture.detectChanges();
    component.isNew = true;
    component.employeeForm.value.firstName = 'test';
    component.employeeForm.value.lastName = 'name';
    component.onCreateEmployee();
    expect(addEmployeeServiceSpy).toHaveBeenCalled();
  });

  it('should call updateEmployee when its not new',  () => {
    fixture.detectChanges();
    component.isNew = false;
    component.employeeForm.value.firstName = 'test';
    component.employeeForm.value.lastName = 'name';
    component.onCreateEmployee();
    expect(updateEmployeeServiceSpy).toHaveBeenCalled();
  });

  it('should get the image for initials',  () => {
    component.imageType = '';
    const response = component.getImage('test', 'name');
    expect(response).toEqual('initials/t_n');
  });

});
