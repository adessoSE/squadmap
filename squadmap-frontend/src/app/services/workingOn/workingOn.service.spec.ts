import {WorkingOnService} from './workingOn.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';
import {WorkingOnModel} from '../../models/workingOn.model';
import {environment} from "../../../environments/environment";

describe('WorkingOnService', () => {
  let service: WorkingOnService;
  let httpMock: HttpTestingController;
  let dummyResponseWorkingOnModel: WorkingOnModel[];
  const date = new Date();

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [WorkingOnService]
    });
    service = TestBed.get(WorkingOnService);
    httpMock = TestBed.get(HttpTestingController);
    dummyResponseWorkingOnModel = [
      {
        workingOnId: 1,
        employee: {
          employeeId: 1,
          firstName: '',
          lastName: '',
          birthday: date,
          email: '',
          phone: '',
          isExternal: false,
          image: '',
          projects: []
        },
        project: {
          projectId: 2,
          title: '',
          description: '',
          since: date,
          until: date,
          isExternal: false,
          sites: [],
          employees: []
        },
        since: date,
        until: date,
        workload: 0
      }, {
        workingOnId: 2,
        employee: {
          employeeId: 2,
          firstName: '',
          lastName: '',
          birthday: date,
          email: '',
          phone: '',
          isExternal: false,
          image: '',
          projects: []
        },
        project: {
          projectId: 3,
          title: '',
          description: '',
          since: date,
          until: date,
          isExternal: false,
          sites: [],
          employees: []
        },
        since: date,
        until: date,
        workload: 0
      }
    ];
  });

  afterAll(() => {
    httpMock.verify();
  });

  it('should be created',  () => {
    expect(TestBed.get(WorkingOnService)).toBeTruthy();
  });

  it('should be able to create a WorkingOn', () => {
    const newDummyWorkingOnModel: CreateWorkingOnModel = {
      employeeId: 1,
      projectId: 2,
      since: date,
      until: date,
      workload: 0
    };
    service.createWorkingOn(newDummyWorkingOnModel).subscribe(() => {
      expect(dummyResponseWorkingOnModel[0].workingOnId).toBe(1);
    });
    const request = httpMock.expectOne(environment.base_api_url + '/workingOn/create');
    expect(request.request.method).toBe('POST');
    request.flush(dummyResponseWorkingOnModel);
  });

  it('should be able to delete a WorkingOn', () => {
    service.deleteWorkingOn(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyResponseWorkingOnModel));
    });
    const request = httpMock.expectOne(environment.base_api_url + '/workingOn/delete/1');
    expect(request.request.method).toBe('DELETE');
    dummyResponseWorkingOnModel.splice(0, 1);
    request.flush(dummyResponseWorkingOnModel);
  });


  it('should be able to update a workingOn', () => {
    service.updateWorkingOn(1, 1, 3, date, date, 0).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyResponseWorkingOnModel));
    });
    const request = httpMock.expectOne(environment.base_api_url + '/workingOn/update/1');
    expect(request.request.method).toBe('PUT');
    dummyResponseWorkingOnModel[0].project.projectId = 3;
    request.flush(dummyResponseWorkingOnModel);
  });

  it('should be able to get one WorkingOn', () => {
    service.getWorkingOn(1).subscribe(res => {
      expect(JSON.stringify(res)).toEqual(JSON.stringify(dummyResponseWorkingOnModel[0]));
    });
    const request = httpMock.expectOne(environment.base_api_url + '/workingOn/1');
    expect(request.request.method).toBe('GET');
    request.flush(dummyResponseWorkingOnModel[0]);
  });
});
