import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateWorkingOnProjectModalComponent } from './update-working-on-project-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";

describe('UpdateWorkingOnProjectModalComponent', () => {
  let component: UpdateWorkingOnProjectModalComponent;
  let fixture: ComponentFixture<UpdateWorkingOnProjectModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateWorkingOnProjectModalComponent ],
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
    fixture = TestBed.createComponent(UpdateWorkingOnProjectModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
