import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {ProjectService} from '../service/project.service';
import {CreateProjectModel} from '../models/createProject.model';
import {ProjectModel} from '../models/project.model';

@Component({
  selector: 'app-project-modal',
  templateUrl: './project-modal.component.html',
  styleUrls: ['./project-modal.component.css']
})
export class ProjectModalComponent implements OnInit {
  project: ProjectModel;
  isNew: boolean;
  actionName: string;

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService) { }

  ngOnInit() {
    if (!this.project) {
      this.project = new ProjectModel(0, '', '', new Date(), new Date(), false, []);
      this.isNew = true;
    }
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
      this.projectService.addProject(newProject).subscribe(res => {
        console.log(res);
        this.modalRef.hide();
        location.reload();
      });
    } else {
      this.projectService.updateProject(newProject, this.project.projectId).subscribe(res => {
        console.log(res);
        this.modalRef.hide();
        location.reload();
      });
    }
  }
}
