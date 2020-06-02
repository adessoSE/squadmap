import {TestBed} from '@angular/core/testing';
import {FilterEmployeesPipe} from '../../../pipes/filterEmployees/filterEmployees.pipe';
import {BsModalService, ModalModule, TabsModule} from 'ngx-bootstrap';
import {FormsModule} from '@angular/forms';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeDetailComponent} from "./employee-detail.component";
import {EmployeeModel} from "../../../models/employee.model";
import {WorkingOnProjectModel} from "../../../models/workingOnProject.model";
import {ProjectModel} from "../../../models/project.model";
import {MapComponent} from "../../map-view/map/map.component";
import {IconsModule} from "../../../icons/icons.module";

describe('EmployeeDetailComponent', () => {
  let fixture;
  let component;
  let bsModalService;
  let showModalServiceSpy;

  const dummyEmployeeAPI: EmployeeModel = {
    employeeId: 1,
    firstName: 'Test1',
    lastName: 'Name',
    birthday: new Date(),
    email: 'test3@name.de',
    phone: '0123456789',
    isExternal: false,
    image: '',
    projects: []
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        EmployeeDetailComponent,
        MapComponent,
        FilterEmployeesPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule,
        RouterTestingModule,
        TabsModule.forRoot(),
        IconsModule,
      ],
      providers: [BsModalService]
    });
    fixture = TestBed.createComponent(EmployeeDetailComponent);
    component = fixture.componentInstance; // to access properties and methods

    bsModalService = fixture.debugElement.injector.get(BsModalService);
    showModalServiceSpy = spyOn(bsModalService, 'show');

  });

  it('should create the EmployeeDetailComponent',  () => {
    expect(component).toBeDefined();
  });

  it('should the show method when opening add project modal',  () => {
    component.employee = dummyEmployeeAPI;
    component.onOpenAddProjectModal();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should the show method when opening editing project',  () => {
    component.employee = dummyEmployeeAPI;
    component.onEditProject(component.employee.projects);
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should the show method when updating',  () => {
    component.employee = dummyEmployeeAPI;
    component.onUpdate();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should filter existing Projects',  () => {
    let dummyProjects = [
      {
        projectId: 1,
        title: 'Test1',
        description: 'Description1',
        since: new Date(),
        until: new Date(),
        isExternal: false,
        sites: [],
        employees: []
      },
      {
        projectId: 2,
        title: 'Test2',
        description: 'Description2',
        since: new Date(),
        until: new Date(),
        isExternal: false,
        sites: [],
        employees: []
      },
    ];
    const dummyEmployee: EmployeeModel = {
      employeeId: 1,
      firstName: 'Test1',
      lastName: 'Name',
      birthday: new Date(),
      email: 'test3@name.de',
      phone: '0123456789',
      isExternal: false,
      image: '',
      projects: [new WorkingOnProjectModel(5, dummyProjects[1], new Date(), new Date(), 20)]
    };
    let filteredProjects: ProjectModel[] = component.filterProjects(dummyProjects, dummyEmployee.projects);
    dummyProjects.splice(1,1);
    expect(JSON.stringify(filteredProjects)).toEqual(JSON.stringify(dummyProjects));
  });
});
