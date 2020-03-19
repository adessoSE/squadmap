import {TestBed} from "@angular/core/testing";
import {ProjectDetailComponent} from "./project-detail.component";
import {MapProjectDetailComponent} from "../../map-view/map-project-detail/map-project-detail.component";
import {BsModalService, ModalModule, TabsModule} from "ngx-bootstrap";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('Project Detail Component', () => {
  let fixture;
  let component;
  let bsModalService;
  let showModalServiceSpy;

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
        ProjectDetailComponent,
        MapProjectDetailComponent
      ],
      imports: [
        TabsModule.forRoot(),
        ModalModule.forRoot(),
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      providers: [
        BsModalService
      ]
    });
    fixture = TestBed.createComponent(ProjectDetailComponent);
    component = fixture.componentInstance;

    bsModalService = fixture.debugElement.injector.get(BsModalService);
    showModalServiceSpy = spyOn(bsModalService, 'show');

    component.project = dummyProject;
  });

  it('should create the Project Detail Component',  () => {
    expect(component).toBeDefined();
  });

  it('should call the show method when opening add employee modal',  () => {
    component.onOpenAddEmployeeModal();
    expect(showModalServiceSpy).toHaveBeenCalled();
  });
});
