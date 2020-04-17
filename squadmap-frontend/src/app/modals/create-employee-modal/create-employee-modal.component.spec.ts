import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateEmployeeModalComponent} from './create-employee-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {EmployeeService} from "../../services/employee/employee.service";
import {IconsModule} from "../../icons/icons.module";

describe('CreateEmployeeModalComponent', () => {
  let component: CreateEmployeeModalComponent;
  let fixture: ComponentFixture<CreateEmployeeModalComponent>;

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
        BsModalRef,
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
});
