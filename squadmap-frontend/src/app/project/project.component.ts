import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ProjectService} from '../service/project.service';
import {Router} from '@angular/router';
import {CreateProjectModel} from '../models/createProject.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ProjectModalComponent} from '../project-modal/project-modal.component';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css'],
})
export class ProjectComponent implements OnInit {

  private projectList: ProjectModel[] = [];
  public searchText: string;
  public checkedOldProjects: boolean;
  public checkedExternalProjects: boolean;
  private modalRef: BsModalRef;


  constructor(private projectService: ProjectService, private router: Router, private modalService: BsModalService) { }

  ngOnInit() {
    this.projectService.getProjects().subscribe(() => {
     this.projectList =  this.projectService.projects;
    });
  }

  onOpenProject(project: ProjectModel) {
    this.router.navigate(['/project/' + project.projectId]);
  }

  onDelete(project: ProjectModel) {
    this.projectService.deleteProject(project).subscribe(() => {
      this.projectService.getProjects().subscribe(() => {
        this.projectList =  this.projectService.projects;
      });
    });
  }

  onUpdate(project: ProjectModel) {
    const initialState = {
      project,
      actionName: 'Update'
    };
    this.modalRef = this.modalService.show(ProjectModalComponent, {initialState});
  }

  onAddProject() {
    const dummyProject = new CreateProjectModel('Test-title', 'description', new Date(), new Date(), true);
    this.projectService.addProject(dummyProject).subscribe(res => {
      this.projectService.getProject(+res).subscribe(project => {
        project.since = new Date(project.since);
        project.until = new Date(project.until);
        this.projectList.push(project);
      });
    });
  }
}
