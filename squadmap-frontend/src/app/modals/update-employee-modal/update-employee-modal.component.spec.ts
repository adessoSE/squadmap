import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmployeeModalComponent } from './update-employee-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {IconsModule} from "../../icons/icons.module";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {EmployeeService} from "../../services/employee/employee.service";
import {EmployeeModel} from "../../models/employee.model";
import {Observable} from "rxjs";

describe('UpdateEmployeeModalComponent', () => {
  let component: UpdateEmployeeModalComponent;
  let fixture: ComponentFixture<UpdateEmployeeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateEmployeeModalComponent ],
      imports: [
        ModalModule.forRoot(),
        IconsModule,
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        BsModalService,
        BsModalRef,
        EmployeeService,
        FormBuilder
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateEmployeeModalComponent);
    component = fixture.componentInstance;
    component.employee = new EmployeeModel(1,'','',new Date(), '','',false,'',[]);
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

  it('should call the updateEmployee method',  () => {
    let spy = spyOn(component.employeeService, 'updateEmployee').and.returnValue(new Observable());
    component.onSubmit();
    expect(spy).toHaveBeenCalled();
  });
});
