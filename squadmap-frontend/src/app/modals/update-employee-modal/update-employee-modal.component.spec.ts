import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmployeeModalComponent } from './update-employee-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {IconsModule} from "../../icons/icons.module";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {EmployeeService} from "../../services/employee/employee.service";
import {EmployeeModel} from "../../models/employee.model";

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
});
