import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {ProjectService} from '../../services/project/project.service';
import {CreateProjectModel} from '../../models/createProject.model';
import {ProjectModel} from '../../models/project.model';
import {DateFormatterService} from '../../services/dateFormatter/dateFormatter.service';

@Component({
  selector: 'app-project-modal',
  templateUrl: './project-modal.component.html',
  styleUrls: ['./project-modal.component.css']
})
export class ProjectModalComponent implements OnInit {
  project: ProjectModel;
  isNew: boolean;
  actionName: string;
  since: string;
  until: string;
  errorOccurred: boolean;
  errorMessage: string;
  sitesString: string;

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService,
              private dateFormatter: DateFormatterService) { }

  ngOnInit() {
    if (!this.project) {
      this.project = new ProjectModel(0, '', '', new Date(), new Date(), false, [], []);
      this.sitesString = '';
      this.isNew = true;
    } else {
      if (!this.project.sites) {
        this.project.sites = [];
      }
      this.sitesString = this.project.sites.join(', ');
    }
    // format necessary in order to prefill the form
    this.since = this.dateFormatter.formatDate(this.project.since);
    this.until = this.dateFormatter.formatDate(this.project.until);
  }

  onCreateProject(projectForm: NgForm) {
    let sites: string[] = [];
    if (projectForm.value.sites) {
      sites = projectForm.value.sites.split(',');
      sites = sites.map( url => url.trim());
    }
    const newProject = new CreateProjectModel(
      projectForm.value.title,
      projectForm.value.description,
      new Date(projectForm.value.since),
      new Date(projectForm.value.until),
      projectForm.value.isExternal,
      sites
    );
    if (this.isNew) {
      this.projectService.addProject(newProject).subscribe(() => {
        this.closeModal();
      }, error => {
        this.handleError(error.error.message);
      });
    } else {
      this.projectService.updateProject(newProject, this.project.projectId).subscribe(() => {
        this.closeModal();
      }, error => {
        this.handleError(error.error.message);
      });
    }
  }

  private closeModal() {
    this.modalRef.hide();
    location.reload();
  }

  private handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }
}
