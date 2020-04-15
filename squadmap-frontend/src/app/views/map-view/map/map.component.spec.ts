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
    component.employees = employees;
    component.projects = projects;
  });

  it('should be created', () => {
    expect(component).toBeDefined();
    expect(component.network).toBeDefined();
  });


  it('should create the map', () => {
    component.createMap();
    const amountOfNodes = Object.keys(component.network.getPositions()).length;
    expect(amountOfNodes).toEqual(3);
  });

  it('should be able to delete a node',  () => {
    component.createMap();
    component.network.selectNodes([1]);
    component.network.deleteSelected();
    const amountOfNodes = Object.keys(component.network.getPositions()).length;
    expect(amountOfNodes).toEqual(2);
  });

  it('should be able to delete an edge',  () => {
    component.createMap();
    component.network.selectEdges([4]);
    component.network.deleteSelected();
    expect(component.network.selectEdges([4])).toBeUndefined();
  });

  it('should check if the given correct id is an existing Employee',  () =>{
    expect(component.isEmployee(1)).toBeTruthy();
  });

  it('should check if the given incorrect id is an existing Employee',  () =>{
    expect(component.isEmployee(3)).toBeFalsy();
  });

  it('should check if the given correct id is an existing Project',  () =>{
    expect(component.isProject(3)).toBeTruthy();
  });

  it('should check if the given incorrect id is an existing Project',  () =>{
    expect(component.isProject(4)).toBeFalsy();
  });

  it('should refresh the component',  () =>{
    expect(component.projects.length).toBeGreaterThan(0);
    expect(component.employees.length).toBeGreaterThan(0);
    component.refresh();
    expect(component.projects.length).toEqual(0);
    expect(component.employees.length).toEqual(0);
  });

  it('should be able to toggle Physics',  () =>{
    expect(component.isPhysicsEnabled).toBeFalsy();
    component.togglePhysics();
    expect(component.isPhysicsEnabled).toBeTruthy();
    component.togglePhysics();
    expect(component.isPhysicsEnabled).toBeFalsy();
  });
});
