import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {ProjectService} from '../service/project.service';
import {CreateProjectModel} from '../models/createProject.model';
import {ProjectModel} from '../models/project.model';
import {DateFormatterService} from '../service/dateFormatter.service';

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

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService,
              private dateFormatter: DateFormatterService) { }

  ngOnInit() {
    if (!this.project) {
      this.project = new ProjectModel(0, '', '', new Date(), new Date(), false, []);
      this.isNew = true;
    }
    // format necessary in order to prefill the form
    this.since = this.dateFormatter.formatDate(this.project.since);
    this.until = this.dateFormatter.formatDate(this.project.until);

  }

  onCreateProject(projectForm: NgForm) {
    const newProject = new CreateProjectModel(
      projectForm.value.title,
      projectForm.value.description,
      new Date(projectForm.value.since),
      new Date(projectForm.value.until),
      projectForm.value.isExternal
    );
    console.log(newProject);
    if (this.isNew) {
      this.projectService.addProject(newProject).subscribe(() => {
        this.modalRef.hide();
        location.reload();
      });
    } else {
      this.projectService.updateProject(newProject, this.project.projectId).subscribe(() => {
        this.modalRef.hide();
        location.reload();
      });
    }
  }
}
