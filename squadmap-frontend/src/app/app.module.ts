import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {NavigationComponent} from './navigation/navigation.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {ModalModule} from 'ngx-bootstrap';
import {ProjectComponent} from './project/project.component';
import {EmployeeComponent} from './employee/employee.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {FilterEmployeesPipe} from './pipes/filterEmployees.pipe';
import {FilterProjectsPipe} from './pipes/filterProjects.pipe';
import {EmployeeDetailComponent} from './employee-detail/employee-detail.component';
import { ProjectDetailComponent } from './project-detail/project-detail.component';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { MapComponent } from './map/map.component';

const routes: Routes = [
  {
    path: '',
    component: MapComponent
  },
  {
    path: 'map',
    component: MapComponent
  },
  {
    path: 'employee',
    component: EmployeeComponent
  },
  {
    path: 'employee/:id',
    component: EmployeeDetailComponent
  },
  {
    path: 'project',
    component: ProjectComponent
  },
  {
    path: 'project/:id',
    component: ProjectDetailComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    EmployeeComponent,
    ProjectComponent,
    PageNotFoundComponent,
    FilterProjectsPipe,
    FilterEmployeesPipe,
    EmployeeDetailComponent,
    ProjectDetailComponent,
    MapComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    ModalModule.forRoot(),
    RouterModule.forRoot(routes),
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    ButtonsModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: []
})
export class AppModule {
}
