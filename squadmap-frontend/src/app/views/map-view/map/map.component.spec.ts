import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MapComponent} from './map.component';
import {EmployeeService} from '../../../services/employee/employee.service';
import {ProjectService} from '../../../services/project/project.service';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {FormsModule} from '@angular/forms';
import {BsModalService, ComponentLoaderFactory, ModalModule, PositioningService} from 'ngx-bootstrap';
import {IconsModule} from '../../../icons/icons.module';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterTestingModule} from '@angular/router/testing';
import {Observable} from "rxjs";
import {WorkingOnProjectModel} from "../../../models/workingOnProject.model";
import {MessageModalComponent} from "../../../modals/message-modal/message-modal.component";
import {ActivatedRoute} from "@angular/router";
import {EmployeeModel} from "../../../models/employee.model";
import {ProjectModel} from "../../../models/project.model";

export let projects = [
  {
    projectId: 3,
    title: 'Test1',
    description: 'Description1',
    since: new Date(),
    until: new Date(),
    isExternal: false,
    sites: [],
    employees: []
  },
];
export let employees = [
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
    projects: [new WorkingOnProjectModel(4, projects[0],new Date(), new Date(), 50)]
  },
];

export const employeeServiceStub = {
  employees,
  addEmployee(): Observable<Object> {
    return new Observable<Object>();
  },
  getEmployees(): Observable<Object> {
    return new Observable<Object>();
  },
  getEmployee():Observable<Object> {
    return new Observable<Object>();
  },
  deleteEmployee(id: number): Observable<Object>{
    return new Observable<Object>();
  }
};

export const projectServiceStub = {
  projects,
  getProjects(): Observable<Object> {
    return new Observable<Object>();
  }
};
export const workingOnServiceStub: Partial<WorkingOnService> = {};

export const modalServiceStub = {
  show(){}
};

const activatedRouteStub = {
  snapshot: {
    url: [{path: ''}]
  }
};

describe('MapComponent', () => {
  let fixture: ComponentFixture<MapComponent>;
  let component: MapComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        IconsModule,
        RouterTestingModule,
        ModalModule.forRoot()
      ],
      declarations: [
        MapComponent,
        MessageModalComponent
      ],
      providers: [
        {provide: EmployeeService, useValue: employeeServiceStub},
        {provide: ProjectService, useValue: projectServiceStub},
        {provide: WorkingOnService, useValue: workingOnServiceStub},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        // BsModalService,
        {provide: BsModalService, useValue: modalServiceStub},
        ComponentLoaderFactory,
        PositioningService
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    });
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
  });

  it('should be created', () => {
    component.ngOnInit();
    expect(component).toBeDefined();
    expect(component.network).toBeDefined();
  });


  it('should create the map', () => {
    let spyGetNodeData = spyOn(component, 'getNodeData').and.returnValue({nodeList: [], edgeList: []});
    let spyInitializeCurserOptions = spyOn(component, 'initializeCurserOptions');
    let spyGetLayout = spyOn(component, 'getLayout');
    component.createMap();
    expect(spyGetNodeData).toHaveBeenCalled();
    expect(spyInitializeCurserOptions).toHaveBeenCalled();
    expect(spyGetLayout).toHaveBeenCalled();
    expect(component.network).toBeDefined();
  });

  it('should check if the given correct id is an existing Employee',  () =>{
    component.employees = [];
    component.employees.push(new EmployeeModel(1,'','',new Date(), '','',false,'',[]));
    expect(component.isEmployee(1)).toBeTruthy();
  });

  it('should check if the given incorrect id is an existing Employee',  () =>{
    expect(component.isEmployee(3)).toBeFalsy();
  });

  it('should check if the given correct id is an existing Project',  () =>{
    component.projects = [];
    component.projects.push(new ProjectModel(3,'','',new Date(), new Date(), false, [], []));
    expect(component.isProject(3)).toBeTruthy();
  });

  it('should check if the given incorrect id is an existing Project',  () =>{
    expect(component.isProject(4)).toBeFalsy();
  });

  it('should refresh the component',  () =>{
    let spy = spyOn(component.employeeService, 'getEmployees').and.returnValue(new Observable());
    component.refresh();
    expect(component.projects.length).toEqual(0);
    expect(component.employees.length).toEqual(0);
    expect(spy).toHaveBeenCalled();
  });

  it('should be able to toggle Physics',  () =>{
    expect(component.isPhysicsEnabled).toBeFalsy();
    let spy = spyOn(component.network, 'setOptions');
    component.togglePhysics();
    expect(component.isPhysicsEnabled).toBeTruthy();
    expect(spy).toHaveBeenCalled();
    component.togglePhysics();
    expect(component.isPhysicsEnabled).toBeFalsy();
    expect(spy).toHaveBeenCalled();
  });
});
