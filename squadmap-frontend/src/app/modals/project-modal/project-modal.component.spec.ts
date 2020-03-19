import {TestBed} from "@angular/core/testing";
import {FilterProjectsPipe} from "../../pipes/filterProjects/filterProjects.pipe";
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {ProjectModalComponent} from "./project-modal.component";
import {ProjectService} from "../../services/project/project.service";

describe('Project Modal', () => {
  let fixture;
  let component;
  let projectService: ProjectService;
  let addProjectServiceSpy;
  let updateProjectServiceSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProjectModalComponent,
        FilterProjectsPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        HttpClientTestingModule,
      ],
      providers: [BsModalService,
        ProjectService,
        DateFormatterService,
        BsModalRef],
    });
    fixture = TestBed.createComponent(ProjectModalComponent);
    component = fixture.componentInstance;
    projectService = fixture.debugElement.injector.get(ProjectService);
    addProjectServiceSpy = spyOn(projectService, 'addProject').and.callThrough();
    updateProjectServiceSpy = spyOn(projectService, 'updateProject').and.callThrough();
  });

  it('should create the modal', () =>  {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
    // TODO check error message on DOM Element
  });

  // it('should call addProject when its new',  () =>{
  //   fixture.detectChanges();
  //   component.isNew = true;
  //   component.projectForm.value.title = 'title';
  //   component.projectForm.value.description = 'test';
  //   component.projectForm.value.since = '2019-01-01';
  //   component.projectForm.value.until = '2020-01-01';
  //   component.projectForm.value.isExternal = false;
  //   component.onCreateEmployee();
  //   expect(addProjectServiceSpy).toHaveBeenCalled();
  // });


});
