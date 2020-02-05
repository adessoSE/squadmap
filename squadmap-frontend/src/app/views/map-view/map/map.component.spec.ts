import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MapComponent} from './map.component';
import {EmployeeService} from '../../../services/employee/employee.service';
import {ProjectService} from '../../../services/project/project.service';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {FormsModule} from '@angular/forms';
import {BsModalService, ComponentLoaderFactory, PositioningService} from 'ngx-bootstrap';

describe('MapComponent', () => {
  let fixture: ComponentFixture<MapComponent>;
  let component: MapComponent;
  let element;

  const employeeServiceStub: Partial<EmployeeService> = {};
  const projectServiceStub: Partial<ProjectService> = {};
  const workingOnServiceStub: Partial<WorkingOnService> = {};

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule
      ],
      declarations: [
        MapComponent
      ],
      providers: [
        {provide: EmployeeService, useValue: employeeServiceStub},
        {provide: ProjectService, useValue: projectServiceStub},
        {provide: WorkingOnService, useValue: workingOnServiceStub},
        BsModalService,
        ComponentLoaderFactory,
        PositioningService
      ]
    });
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
  });

  it('should be created', () => {
    expect(component).toBeDefined();
  });
});
