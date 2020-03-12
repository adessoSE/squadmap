import {fakeAsync, TestBed} from '@angular/core/testing';
import {BsModalService, ModalModule, TabsModule} from 'ngx-bootstrap';
import {EmployeeComponent} from './employee.component';
import {FormsModule} from '@angular/forms';
import {FilterEmployeesPipe} from '../../../pipes/filterEmployees/filterEmployees.pipe';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeModel} from '../../../models/employee.model';
import {Router} from "@angular/router";
import {routes} from "../../../app.routing";
import {MapComponent} from "../../map-view/map/map.component";
import {ProjectComponent} from "../../project-view/project/project.component";
import {EmployeeDetailComponent} from "../employee-detail/employee-detail.component";
import {ProjectDetailComponent} from "../../project-view/project-detail/project-detail.component";
import {MapProjectDetailComponent} from "../../map-view/map-project-detail/map-project-detail.component";
import {MapEmployeeDetailComponent} from "../../map-view/map-employee-detail/map-employee-detail.component";
import {PageNotFoundComponent} from "../../page-not-found-view/page-not-found.component";
import {IconsModule} from "../../../icons/icons.module";
import {FilterProjectsPipe} from "../../../pipes/filterProjects/filterProjects.pipe";

describe('EmployeeComponent', () => {
  let fixture;
  let component;
  let element;
  let bsModalService;
  let showModalServiceSpy;
  let router;
  let routerNavigateSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        EmployeeComponent,
        MapComponent,
        EmployeeComponent,
        ProjectComponent,
        EmployeeDetailComponent,
        ProjectDetailComponent,
        MapProjectDetailComponent,
        MapEmployeeDetailComponent,
        PageNotFoundComponent,
        FilterEmployeesPipe,
        FilterProjectsPipe
      ],
      imports: [
        ModalModule.forRoot(),
        FormsModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes(routes),
        IconsModule,
        TabsModule
      ],
      providers: [BsModalService]
    });
    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance; // to access properties and methods
    element = fixture.nativeElement; // to access DOM element

    bsModalService = fixture.debugElement.injector.get(BsModalService);
    showModalServiceSpy = spyOn(bsModalService, 'show');

    router = fixture.debugElement.injector.get(Router);
    routerNavigateSpy = spyOn(router, 'navigate').and.callThrough();


  });

  it('should create the EmployeeComponent',  () => {
    expect(component).toBeDefined();
  });

  it('should create the search box', () => {
    expect(element.querySelector('#searchText')).toBeTruthy();
  });

  it('should create the table', () => {
    expect(element.querySelector('table')).toBeTruthy();
  });

  it('should add 1 row to table', () => {
     fixture.employees = [];
     fixture.employees.push(new EmployeeModel(1, 'test', 'test', new Date(), '', '', false, '', []));
     const table = element.querySelector('table');
     expect(table.rows.length).toBe(1);
  });

  it('should call the show method when starting to edit',  () => {
    component.onEdit();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });

  it('should call the show method when adding',  () => {
    component.onAddEmployee();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });


  it('should navigate you to the profile',  fakeAsync(() => {
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
    component.onOpenEmployeeProfile(dummyEmployeeAPI);
    expect(routerNavigateSpy).toHaveBeenCalled();
  }));
});
