import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../../../models/project.model';
import {ProjectService} from '../../../services/project/project.service';
import {Router} from '@angular/router';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ProjectModalComponent} from '../../../modals/project-modal/project-modal.component';

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
    this.projectService.deleteProject(project.projectId).subscribe(() => {
      this.projectService.getProjects().subscribe(() => {
        this.projectList =  this.projectService.projects;
      });
    });
  }

  onUpdate(project: ProjectModel) {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        project,
        actionName: 'Update'
      }
    };
    this.modalRef = this.modalService.show(ProjectModalComponent, config);
  }

  onAddProject() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        actionName: 'Add'
      }
    };
    this.modalRef = this.modalService.show(ProjectModalComponent, config);
  }
}
