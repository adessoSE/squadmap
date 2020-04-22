import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewWorkingOnModalComponent } from './new-working-on-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {Observable} from "rxjs";

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

  it('should create', () => {
    component.ngOnInit();
    expect(component).toBeTruthy();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
  });

  it('should call the createWorkingOn method',  () => {
    let date = new Date();
    let edgeData = {
      from: date,
      until: date
    }
    component.edgeData=edgeData;
    let spy = spyOn(component.workingOnService, 'createWorkingOn').and.returnValue(new Observable());
    component.onSubmit();
    expect(spy).toHaveBeenCalled();
  });
});
