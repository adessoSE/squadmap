import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProjectModalComponent } from './update-project-modal.component';
import {BsModalRef, BsModalService, ModalModule} from "ngx-bootstrap";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {ProjectService} from "../../services/project/project.service";
import {MapComponent} from "../../views/map-view/map/map.component";

describe('UpdateProjectModalComponent', () => {
  let component: UpdateProjectModalComponent;
  let fixture: ComponentFixture<UpdateProjectModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateProjectModalComponent ],
      imports: [
        ModalModule.forRoot(),
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        BsModalService,
        BsModalRef,
        DateFormatterService,
        ProjectService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProjectModalComponent);
    component = fixture.componentInstance;
    component.project = {
      projectId: 3,
      title: 'Test1',
      description: 'Description1',
      since: new Date(),
      until: new Date(),
      isExternal: false,
      sites: [],
      employees: []
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
