import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateProjectModalComponent} from './update-project-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {ProjectService} from "../../services/project/project.service";
import {Observable} from "rxjs";
import {ShowErrorMessageComponent} from "../error-messages/show-error-message.component";

describe('UpdateProjectModalComponent', () => {
  let component: UpdateProjectModalComponent;
  let fixture: ComponentFixture<UpdateProjectModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateProjectModalComponent, ShowErrorMessageComponent],
      imports: [
        ModalModule.forRoot(),
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        BsModalService,
        BsModalRef,
        DateFormatterService,
        ProjectService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProjectModalComponent);
    component = fixture.componentInstance;
    component.project = {
      projectId: 3,
      title: 'Test1',
      description: 'Description1',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
      employees: []
    };
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

  it('should call the updateProject method',  () => {
    let spy = spyOn(component.projectService, 'updateProject').and.returnValue(new Observable());
    component.onSubmit();
    expect(spy).toHaveBeenCalled();
  });
});
