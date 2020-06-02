import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateEmployeeModalComponent} from './create-employee-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {EmployeeService} from "../../services/employee/employee.service";
import {IconsModule} from "../../icons/icons.module";
import {Observable} from "rxjs";

let modalRef = {
  hide(){}
}

describe('CreateEmployeeModalComponent', () => {
  let component: CreateEmployeeModalComponent;
  let fixture: ComponentFixture<CreateEmployeeModalComponent>;

  let employeeServiceSpy;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEmployeeModalComponent ],
      imports: [
        ModalModule.forRoot(),
        IconsModule,
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        BsModalService,
        {provide: BsModalRef , useValue: modalRef},
       EmployeeService,
        FormBuilder
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEmployeeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
  });

  it('should generate a random string of length 5',  () => {
    expect(component.generateRandomString().length).toEqual(5);
  });

  it('should change the seed to initials',  () => {
    component.changeSeed();
    expect(component.imageSeed).toEqual('initials/_');
  });

  it('should change the seed to an imageType',  () => {
    component.form.value.imageType = 'human'
    component.changeSeed();
    expect(component.imageSeed).toContain('human');
  });


  it('should call the addEmployee method',  () => {
    let spyEmployeeService = spyOn(component.employeeService, 'addEmployee').and.returnValue(new Observable());
    component.onSubmit();
    expect(spyEmployeeService).toHaveBeenCalled();
  });

});
