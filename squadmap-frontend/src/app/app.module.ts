import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {NavigationComponent} from './navigation/navigation.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {ModalModule, TabsModule} from 'ngx-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {FilterEmployeesPipe} from './pipes/filterEmployees/filterEmployees.pipe';
import {FilterProjectsPipe} from './pipes/filterProjects/filterProjects.pipe';
import {ProjectModalComponent} from './modals/project-modal/project-modal.component';
import {EmployeeModalComponent} from './modals/employee-modal/employee-modal.component';
import {ButtonsModule} from 'ngx-bootstrap/buttons';
import {AddEmployeeModalComponent} from './modals/add-employee-modal/add-employee-modal.component';
import {AddProjectModalComponent} from './modals/add-project-modal/add-project-modal.component';
import {MessageModalComponent} from './modals/message-modal/message-modal.component';
import {AppRoutingModule, routingComponents} from './app.routing';
import {MapEmployeeDetailComponent} from './views/map-view/map-employee-detail/map-employee-detail.component';
import {IconsModule} from './icons/icons.module';
import {NewWorkingOnModalComponent} from './modals/new-working-on-modal/new-working-on-modal.component';
import {UpdateWorkingOnProjectModalComponent} from './modals/update-working-on-project-modal/update-working-on-project-modal.component';
import {UpdateWorkingOnEmployeeModalComponent} from './modals/update-working-on-employee-modal/update-working-on-employee-modal.component';


@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    FilterProjectsPipe,
    FilterEmployeesPipe,
    ProjectModalComponent,
    EmployeeModalComponent,
    AddEmployeeModalComponent,
    AddProjectModalComponent,
    MessageModalComponent,
    routingComponents,
    MapEmployeeDetailComponent,
    NewWorkingOnModalComponent,
    UpdateWorkingOnProjectModalComponent,
    UpdateWorkingOnEmployeeModalComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    ModalModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ButtonsModule.forRoot(),
    TabsModule.forRoot(),
    IconsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    ProjectModalComponent,
    EmployeeModalComponent,
    AddEmployeeModalComponent,
    AddProjectModalComponent,
    MessageModalComponent,
    NewWorkingOnModalComponent,
    UpdateWorkingOnProjectModalComponent,
    UpdateWorkingOnEmployeeModalComponent
  ]
})
export class AppModule {
}
