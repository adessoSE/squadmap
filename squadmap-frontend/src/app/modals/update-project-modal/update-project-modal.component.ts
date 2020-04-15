import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap";
import {ProjectService} from "../../services/project/project.service";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {CreateProjectModel} from "../../models/createProject.model";
import {ProjectModel} from "../../models/project.model";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";

@Component({
  selector: 'app-update-project-modal',
  templateUrl: './update-project-modal.component.html'
})
export class UpdateProjectModalComponent implements OnInit {

  private project: ProjectModel;

  private errorOccurred: boolean;
  private errorMessage: string;

  private form: FormGroup;

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService,
              private formBuilder: FormBuilder,
              private dateFormatterService: DateFormatterService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      title: [this.project.title,[
        Validators.required,
      ]],
      description: [this.project.description,[
      ]],
      since: [this.dateFormatterService.formatDate(this.project.since),[
        Validators.required,
        minimumDateValidator
      ]],
      until: [this.dateFormatterService.formatDate(this.project.until),[
        Validators.required,
        minimumDateValidator
      ]],
      sitestring: [this.project.sites,[
      ]],
      isExternal: [this.project.isExternal,[
      ]],
    });
  }

  onSubmit() {
    let sites: string[] = [];
    if (this.form.value.sites) {
      sites = this.form.value.sites.split(',');
      sites = sites.map( url => url.trim());
    }
    if(!this.form.value.isExternal){
      this.form.value.isExternal = false;
    }
    this.projectService.updateProject(
      new CreateProjectModel(
        this.form.value.title,
        this.form.value.description,
        this.form.value.since,
        this.form.value.until,
        this.form.value.isExternal,
        sites
      ), this.project.projectId
    ).subscribe(() => {
      this.modalRef.hide();
      location.reload();
    }, error => {
      this.handleError(error.error.message);
    });
  }

  private handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }

}
