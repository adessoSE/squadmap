import {TestBed} from "@angular/core/testing";
import {BsModalRef, ModalModule} from "ngx-bootstrap";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {Observable} from "rxjs";
import {WorkingOnModalComponent} from "./working-on-modal.component";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";

describe('Working On Modal', () =>{
  let fixture;
  let component;
  let workingOnService;
  let createWorkingOnSpy;
  let updateWorkingOnSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        WorkingOnModalComponent,
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        HttpClientTestingModule,
      ],
      providers: [BsModalRef,
        DateFormatterService,
        WorkingOnService
      ],
    });
    fixture = TestBed.createComponent(WorkingOnModalComponent);
    component = fixture.componentInstance;
    workingOnService = fixture.debugElement.injector.get(WorkingOnService);
    createWorkingOnSpy = spyOn(workingOnService, 'createWorkingOn').and.returnValue(new Observable());
    updateWorkingOnSpy = spyOn(workingOnService, 'updateWorkingOn').and.returnValue(new Observable());
  });


  it('should create the modal',  () => {
    fixture.detectChanges();
    expect(component).toBeDefined();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
    // TODO check error message on DOM Element
  });

});
