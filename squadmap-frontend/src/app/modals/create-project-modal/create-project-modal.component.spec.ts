import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateProjectModalComponent} from './create-project-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {ProjectService} from "../../services/project/project.service";
import {Observable} from "rxjs";
import {ShowErrorMessageComponent} from "../error-messages/show-error-message.component";

describe('CreateProjectModalComponent', () => {
  let component: CreateProjectModalComponent;
  let fixture: ComponentFixture<CreateProjectModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateProjectModalComponent, ShowErrorMessageComponent],
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
    fixture = TestBed.createComponent(CreateProjectModalComponent);
    component = fixture.componentInstance;
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

  it('should call the addProject method',  () => {
    let spyProjectService = spyOn(component.projectService, 'addProject').and.returnValue(new Observable());
    Object.keys(component.form.controls).forEach(key => {
      component.form.get(key).clearValidators();
      component.form.get(key).clearAsyncValidators();
      component.form.get(key).updateValueAndValidity();
    });
    component.onSubmit();
    expect(spyProjectService).toHaveBeenCalled();
  });
});
