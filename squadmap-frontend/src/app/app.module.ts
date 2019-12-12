import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {NavigationComponent} from './navigation/navigation.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {ProjectComponent} from './project/project.component';
import {EmployeeComponent} from './employee/employee.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {FormsModule} from '@angular/forms';
import {FilterPipe} from './pipes/filter.pipe';
import {HttpClientModule} from '@angular/common/http';
import {FilterEmployeePipe} from './pipes/filterEmployee.pipe';
import {EmployeeDetailComponent} from './employee-detail/employee-detail.component';

const routes: Routes = [
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
    FilterPipe,
    FilterEmployeePipe,
    EmployeeDetailComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    RouterModule.forRoot(routes),
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
