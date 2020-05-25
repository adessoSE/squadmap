import {RouterModule, Routes} from '@angular/router';
import {MapComponent} from './views/map-view/map/map.component';
import {EmployeeComponent} from './views/employee-view/employee/employee.component';
import {EmployeeDetailComponent} from './views/employee-view/employee-detail/employee-detail.component';
import {ProjectComponent} from './views/project-view/project/project.component';
import {ProjectDetailComponent} from './views/project-view/project-detail/project-detail.component';
import {PageNotFoundComponent} from './views/page-not-found-view/page-not-found.component';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

export const routes: Routes = [
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
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppRoutingModule { }

export const routingComponents = [
  MapComponent,
  EmployeeComponent,
  EmployeeDetailComponent,
  ProjectComponent,
  ProjectDetailComponent,
  PageNotFoundComponent,
];
