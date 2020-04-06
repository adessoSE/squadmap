import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateWorkingOnEmployeeModalComponent } from './update-working-on-employee-modal.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ProjectModalComponent} from "../project-modal/project-modal.component";
import {FilterProjectsPipe} from "../../pipes/filterProjects/filterProjects.pipe";
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ProjectService} from "../../services/project/project.service";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";

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
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
