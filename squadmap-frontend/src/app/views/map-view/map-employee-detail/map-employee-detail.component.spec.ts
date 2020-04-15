import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormsModule} from "@angular/forms";
import {IconsModule} from "../../../icons/icons.module";
import {RouterTestingModule} from "@angular/router/testing";
import {BsModalService, ComponentLoaderFactory, ModalModule} from "ngx-bootstrap";
import {MessageModalComponent} from "../../../modals/message-modal/message-modal.component";
import {EmployeeService} from "../../../services/employee/employee.service";
import {ProjectService} from "../../../services/project/project.service";
import {WorkingOnService} from "../../../services/workingOn/workingOn.service";
import {
  employeeServiceStub,
  modalServiceStub, projects,
  projectServiceStub,
  workingOnServiceStub
} from "../map/map.component.spec";
import {MapEmployeeDetailComponent} from "./map-employee-detail.component";
import {WorkingOnProjectModel} from "../../../models/workingOnProject.model";

const employee = {
  employeeId: 2,
  firstName: 'Test2',
  lastName: 'Name',
  birthday: new Date(),
  email: 'test2@name.de',
  phone: '0123456789',
  isExternal: true,
  image: '',
  projects: [new WorkingOnProjectModel(4, projects[0],new Date(), new Date(), 50)]
};

describe('Map-Employee-Detail-Component', () => {
  let fixture: ComponentFixture<MapEmployeeDetailComponent>;
  let component: MapEmployeeDetailComponent;

  beforeEach(() => {
    TestBed.configureTestingModule( {
      imports: [
        FormsModule,
        IconsModule,
        RouterTestingModule,
        ModalModule.forRoot()
      ],
      declarations: [
        MapEmployeeDetailComponent,
        MessageModalComponent
      ],
      providers: [
        {provide: EmployeeService, useValue: employeeServiceStub},
        {provide: ProjectService, useValue: projectServiceStub},
        {provide: WorkingOnService, useValue: workingOnServiceStub},
        {provide: BsModalService, useValue: modalServiceStub},
        ComponentLoaderFactory,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MapEmployeeDetailComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    component.employee=employee;
  });

  it('should be created', () => {
    expect(component).toBeDefined();
    expect(component.network).toBeDefined();
  });


});
