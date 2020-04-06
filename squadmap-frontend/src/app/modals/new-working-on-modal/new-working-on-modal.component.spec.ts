import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewWorkingOnModalComponent } from './new-working-on-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";

describe('NewWorkingOnModalComponent', () => {
  let component: NewWorkingOnModalComponent;
  let fixture: ComponentFixture<NewWorkingOnModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewWorkingOnModalComponent ],
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
    fixture = TestBed.createComponent(NewWorkingOnModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //
  //   expect(component).toBeTruthy();
  // });
});
