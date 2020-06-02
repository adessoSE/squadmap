import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateWorkingOnEmployeeModalComponent} from './update-working-on-employee-modal.component';
import {ReactiveFormsModule} from "@angular/forms";
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {WorkingOnEmployeeModel} from "../../models/workingOnEmployee.model";
import {EmployeeModel} from "../../models/employee.model";
import {Observable} from "rxjs";

describe('UpdateWorkingOnEmployeeModalComponent', () => {
  let component: UpdateWorkingOnEmployeeModalComponent;
  let fixture: ComponentFixture<UpdateWorkingOnEmployeeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateWorkingOnEmployeeModalComponent ],
      imports: [
        ModalModule.forRoot(),
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        BsModalService,
        BsModalRef,
        DateFormatterService,
        WorkingOnService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateWorkingOnEmployeeModalComponent);
    component = fixture.componentInstance;
    component.workingOnEmployee = new WorkingOnEmployeeModel(1,
      new EmployeeModel(1,'','', new Date(),'','',false, '',[]),new Date(),new Date(),50);
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

  it('should call the updateWorkingOn method',  () => {
    let spy = spyOn(component.workingOnService, 'updateWorkingOn').and.returnValue(new Observable());
    component.onSubmit();
    expect(spy).toHaveBeenCalled();
  });
});
