import {TestBed} from "@angular/core/testing";
import {ProjectDetailComponent} from "./project-detail.component";
import {BsModalService, ModalModule, TabsModule} from "ngx-bootstrap";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {AddEmployeeModalComponent} from "../../../modals/add-employee-modal/add-employee-modal.component";
import {FilterEmployeesPipe} from "../../../pipes/filterEmployees/filterEmployees.pipe";
import {FormsModule} from "@angular/forms";
import {Observable} from "rxjs";
import {WorkingOnEmployeeModel} from "../../../models/workingOnEmployee.model";
import {MapComponent} from "../../map-view/map/map.component";
import {IconsModule} from "../../../icons/icons.module";

describe('Project Detail Component', () => {
  let fixture;
  let component;
  let bsModalService;

  let dummyProject = {
    projectId: 1,
    title: 'Test1',
    description: 'Description1',
    since: new Date(),
    until: new Date(),
    isExternal: false,
    sites: [],
    employees: []
  };

  let dummyEmployees = [
    {
      employeeId: 1,
      firstName: 'Test1',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test1@name.de',
      phone: '0123456789',
      isExternal: false,
      image: '',
      projects: []
    },
    {
      employeeId: 2,
      firstName: 'Test2',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test2@name.de',
      phone: '0123456789',
      isExternal: true,
      image: '',
      projects: []
    },
  ];

  let workingOnEmployee: WorkingOnEmployeeModel = new WorkingOnEmployeeModel(1,{
    employeeId: 2,
    firstName: 'Test2',
    lastName: 'Name',
    birthday: new Date(),
    email: 'test2@name.de',
    phone: '0123456789',
    isExternal: true,
    image: '',
    projects: []
  },new Date(), new Date(),50);


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProjectDetailComponent,
        MapComponent,
        AddEmployeeModalComponent,
        FilterEmployeesPipe
      ],
      imports: [
        TabsModule.forRoot(),
        ModalModule.forRoot(),
        FormsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        IconsModule,
      ],
      providers: [
        BsModalService
      ],
    });
    fixture = TestBed.createComponent(ProjectDetailComponent);
    component = fixture.componentInstance;

    bsModalService = fixture.debugElement.injector.get(BsModalService);
    component.project = dummyProject;
  });

  it('should create the Project Detail Component',  () => {
    expect(component).toBeDefined();
  });

  it('should call the show method when opening add employee modal',  () => {
    let showModalServiceSpy = spyOn(bsModalService, 'show');
    component.onOpenAddEmployeeModal();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should call the deleteWorkingOn method when onDelete',  () => {
    let spy = spyOn(component.workingOnService, 'deleteWorkingOn').and.returnValue(new Observable());
    component.onDelete(1);
    expect(spy).toHaveBeenCalled();
  });

  it('should call the right methods when refreshing the project',  () => {
    let spyProjectService = spyOn(component.projectService, 'getProject').and.returnValue(new Observable());
    component.refreshProject();
    expect(spyProjectService).toHaveBeenCalled();
  });

  it('should call the show method when onEdit',  () => {
    let showModalServiceSpy = spyOn(bsModalService, 'show');
    component.onEdit(workingOnEmployee);
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should call the navigate method when onOpenEmployeeDetail',  () => {
    let spy = spyOn(component.router, 'navigate');
    component.onOpenEmployeeDetail(1);
    expect(spy).toHaveBeenCalled();
  });

  it('should filter the Employees',  () => {
   let filtered = component.filterEmployees(dummyEmployees, [workingOnEmployee]);
   expect(filtered[0].employeeId).toEqual(1);
  });

  it('should call the navigate method when onOpenEmployeeDetail',  () => {
    let spy = spyOn(component.router, 'navigate');
    component.onOpenEmployeeDetail(1);
    expect(spy).toHaveBeenCalled();
  });

  it('should call the getEmployees method when updating the project',  () => {
    let spy = spyOn(component.employeeService, 'getEmployees').and.returnValue(new Observable());
    component.updateFilteredEmployees();
    expect(spy).toHaveBeenCalled();
  });

});
