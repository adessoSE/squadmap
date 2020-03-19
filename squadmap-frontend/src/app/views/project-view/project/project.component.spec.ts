import {TestBed} from "@angular/core/testing";
import {EmployeeDetailComponent} from "../../employee-view/employee-detail/employee-detail.component";
import {BsModalService, ModalModule, TabsModule} from "ngx-bootstrap";
import {RouterTestingModule} from "@angular/router/testing";
import {ProjectService} from "../../../services/project/project.service";
import {NavigationComponent} from "../../../navigation/navigation.component";
import {MapComponent} from "../../map-view/map/map.component";
import {EmployeeComponent} from "../../employee-view/employee/employee.component";
import {ProjectComponent} from "./project.component";
import {ProjectDetailComponent} from "../project-detail/project-detail.component";
import {MapProjectDetailComponent} from "../../map-view/map-project-detail/map-project-detail.component";
import {MapEmployeeDetailComponent} from "../../map-view/map-employee-detail/map-employee-detail.component";
import {PageNotFoundComponent} from "../../page-not-found-view/page-not-found.component";
import {Router} from "@angular/router";
import {BrowserDynamicTestingModule} from "@angular/platform-browser-dynamic/testing";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {IconsModule} from "../../../icons/icons.module";
import {FilterEmployeesPipe} from "../../../pipes/filterEmployees/filterEmployees.pipe";
import {FilterProjectsPipe} from "../../../pipes/filterProjects/filterProjects.pipe";

describe('Project Component', () => {
  let fixture;
  let component;
  let bsModalService;
  let showModalServiceSpy;
  let router;
  let routerNavigateSpy;

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

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        NavigationComponent,
        MapComponent,
        EmployeeComponent,
        ProjectComponent,
        EmployeeDetailComponent,
        ProjectDetailComponent,
        MapProjectDetailComponent,
        MapEmployeeDetailComponent,
        PageNotFoundComponent,
        FilterEmployeesPipe,
        FilterProjectsPipe,
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule,
        RouterTestingModule,
        TabsModule.forRoot(),
        IconsModule
      ],
      providers: [BsModalService,
      ProjectService]
    });
    fixture = TestBed.createComponent(ProjectComponent);
    component = fixture.componentInstance; // to access properties and methods

    bsModalService = fixture.debugElement.injector.get(BsModalService);
    showModalServiceSpy = spyOn(bsModalService, 'show');

    router = fixture.debugElement.injector.get(Router);
    routerNavigateSpy = spyOn(router, 'navigate');
  });

  it('should create the project component',  () => {
    expect(component).toBeDefined();
  });

  it('should call the show method when updating',  () => {
    component.onUpdate(dummyProject);
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should call the show method when adding',  () => {
    component.onAddProject();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });
});
