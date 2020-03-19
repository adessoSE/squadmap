import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {TestBed} from "@angular/core/testing";
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {AddProjectModalComponent} from "./add-project-modal.component";
import {FilterProjectsPipe} from "../../pipes/filterProjects/filterProjects.pipe";
import {ProjectModel} from "../../models/project.model";

describe('Add Project Modal Component', () => {
  let fixture;
  let component;
  let workingOnService: WorkingOnService;
  let workinOnServiceSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        AddProjectModalComponent,
        FilterProjectsPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        HttpClientTestingModule,
      ],
      providers: [BsModalService,
        WorkingOnService,
        BsModalRef]
    });
    fixture = TestBed.createComponent(AddProjectModalComponent);
    component = fixture.componentInstance;
    workingOnService = fixture.debugElement.injector.get(WorkingOnService);
    workinOnServiceSpy = spyOn(workingOnService, 'createWorkingOn').and.callThrough();
    fixture.detectChanges();
  });

  it('should create the modal', () =>  {
    fixture.detectChanges();
    expect(component.errorMessage).toEqual('');
    expect(component).toBeTruthy();
  });

  it('should show an error message in the modal', () => {
    component.handleError('error');
    expect(component.errorOccurred).toBeTruthy();
    expect(component.errorMessage).toEqual('error');
    // TODO check error message on DOM Element
  });

  it('should call the createWorkingOn Method on call of onAddProject',  () => {
    const dummyProject: ProjectModel = {
      projectId: 1,
      title: 'Test1',
      description: 'Description1',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
      employees: []
    };
    const date = new Date();
    component.employeeId = 2;
    component.onAddProject(dummyProject, date, date, 10);
    expect(workinOnServiceSpy).toHaveBeenCalled();
  });
});
