import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateWorkingOnProjectModalComponent } from './update-working-on-project-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {WorkingOnProjectModel} from "../../models/workingOnProject.model";
import {Observable} from "rxjs";

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
    component.workingOnProject = new WorkingOnProjectModel(1, {
      projectId: 1,
      title: 'Test1',
      description: 'Description1',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
      employees: []
    }, new Date(), new Date(), 50 );
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
