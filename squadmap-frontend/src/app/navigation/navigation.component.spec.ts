import {async, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {NavigationComponent} from './navigation.component';
import {BsModalService, ModalModule, TabsModule} from 'ngx-bootstrap';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {MapComponent} from '../views/map-view/map/map.component';
import {EmployeeComponent} from '../views/employee-view/employee/employee.component';
import {ProjectComponent} from '../views/project-view/project/project.component';
import {EmployeeDetailComponent} from '../views/employee-view/employee-detail/employee-detail.component';
import {ProjectDetailComponent} from '../views/project-view/project-detail/project-detail.component';
import {MapProjectDetailComponent} from '../views/map-view/map-project-detail/map-project-detail.component';
import {PageNotFoundComponent} from '../views/page-not-found-view/page-not-found.component';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {FormsModule} from '@angular/forms';
import {FilterProjectsPipe} from '../pipes/filterProjects/filterProjects.pipe';
import {FilterEmployeesPipe} from '../pipes/filterEmployees/filterEmployees.pipe';
import {AppComponent} from '../app.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {routes} from '../app.routing';
import {MapEmployeeDetailComponent} from '../views/map-view/map-employee-detail/map-employee-detail.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';


describe('NavigationComponent - Routing', () => {
  let router: Router;
  let fixture;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        BrowserDynamicTestingModule,
        FormsModule,
        ModalModule.forRoot(),
        HttpClientTestingModule,
        TabsModule.forRoot()
      ],
      declarations: [
        AppComponent,
        NavigationComponent,
        MapComponent,
        EmployeeComponent,
        ProjectComponent,
        EmployeeDetailComponent,
        ProjectDetailComponent,
        MapProjectDetailComponent,
        MapEmployeeDetailComponent,
        PageNotFoundComponent,
        FilterProjectsPipe,
        FilterEmployeesPipe,
      ],
      providers: [
        BsModalService,
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
    router = TestBed.get(Router);
    fixture = TestBed.createComponent(AppComponent);
    router.initialNavigation();
  });

  it('should navigate you to /', fakeAsync(() => {
    router.navigate(['']);
    tick();
    expect(location.pathname.endsWith('')).toBe(true);
  }));
});


describe('NavigationComponent', () => {
  let fixture;
  let element;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        NavigationComponent
      ],
      imports: [
        ModalModule.forRoot(),
      ],
      providers: [BsModalService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
    fixture = TestBed.createComponent(NavigationComponent);
  });

  it('should render the title in the navigation bar',  async(() => {
    fixture.detectChanges();
    fixture.whenStable().then( () => {
      expect(element.querySelector('.navbar-brand').innerText).toBe('Squadmap');
    });
  }));
});

