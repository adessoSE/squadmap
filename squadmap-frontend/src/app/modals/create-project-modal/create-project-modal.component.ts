import {Component, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {ProjectService} from "../../services/project/project.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {minimumDateValidator} from "../../validators/minimum-date-validator";
import {CreateProjectModel} from "../../models/createProject.model";

@Component({
  selector: 'app-create-project-modal',
  templateUrl: './create-project-modal.component.html'
})
export class CreateProjectModalComponent implements OnInit {

  errorOccurred: boolean;
  errorMessage: string;

  private form: FormGroup;

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      title: ['',[
        Validators.required,
      ]],
      description: ['',[
      ]],
      since: ['',[
        Validators.required,
        minimumDateValidator
      ]],
      until: ['',[
        Validators.required,
        minimumDateValidator
      ]],
      sitestring: ['',[
      ]],
      isExternal: ['',[
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
    this.projectService.addProject(
      new CreateProjectModel(
        this.form.value.title,
        this.form.value.description,
        this.form.value.since,
        this.form.value.until,
        this.form.value.isExternal,
        sites
      )
    ).subscribe(() => {
      this.modalRef.hide();
      location.reload();
    }, error => {
      this.handleError(error.error.message);
    });
  }

  handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }
}
