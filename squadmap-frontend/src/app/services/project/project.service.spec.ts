import {ProjectService} from './project.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {EmployeeService} from '../employee/employee.service';
import {ProjectModel} from '../../models/project.model';
import {CreateProjectModel} from '../../models/createProject.model';

describe('ProjectService', () => {
  let service: ProjectService;
  let httpMock: HttpTestingController;

  let dummyProjects: ProjectModel[];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProjectService]
    });
    service = TestBed.get(ProjectService);
    httpMock = TestBed.get(HttpTestingController);

    dummyProjects = [
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
  });

  afterAll(() => {
    httpMock.verify();
  });

  it('should be created',  () => {
    expect(TestBed.get(EmployeeService)).toBeTruthy();
  });

  it('should be able to retrieve all projects from the API via GET', () => {
    service.getProjects().subscribe(() => {
      expect(service.projects.length).toBe(2);
      expect(JSON.stringify(service.projects)).toEqual(JSON.stringify(dummyProjects));
    });
    const request = httpMock.expectOne('http://localhost:8080/api/project/all');
    expect(request.request.method).toBe('GET');
    request.flush(dummyProjects);
  });

  it('should be able to retrieve one specific project from the API via GET', () => {
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
    service.getProject(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyProject));
    });
    const request = httpMock.expectOne('http://localhost:8080/api/project/1');
    expect(request.request.method).toBe('GET');
    request.flush(dummyProject);
  });

  it('should be able to delete a project from the API via DELETE', () => {
    service.deleteProject(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyProjects));
    });
    const request = httpMock.expectOne('http://localhost:8080/api/project/delete/1');
    expect(request.request.method).toBe('DELETE');
    dummyProjects.splice(0, 1);
    request.flush(dummyProjects);
  });

  it('should be able to update a project from the API via PUT',  () => {
    const newDummyProject: CreateProjectModel = {
      title: 'TestNew',
      description: 'Description3',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
    };
    service.updateProject(newDummyProject, 1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyProjects));
    });
    const request = httpMock.expectOne('http://localhost:8080/api/project/update/1');
    expect(request.request.method).toBe('PUT');
    dummyProjects[0] = new ProjectModel(
      1,
      newDummyProject.title,
      newDummyProject.description,
      newDummyProject.since,
      newDummyProject.until,
      newDummyProject.isExternal,
      [],
      []);
    request.flush(dummyProjects);
  });

  it('should be able to add a project from the API via POST',  () => {
    const newDummyProject: CreateProjectModel = {
      title: 'TestNew',
      description: 'Description3',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
    };
    const dummyProjectAPI: ProjectModel = {
        projectId: 1,
        title: 'Test1',
        description: 'Description1',
        since: new Date(),
        until: new Date(),
        isExternal: false,
        sites: [],
        employees: []
      };
    service.addProject(newDummyProject).subscribe(() => {
      expect(dummyProjectAPI.projectId).toBe(1);
    });
    const request = httpMock.expectOne('http://localhost:8080/api/project/create');
    expect(request.request.method).toBe('POST');
    request.flush(dummyProjectAPI);
  });
});
