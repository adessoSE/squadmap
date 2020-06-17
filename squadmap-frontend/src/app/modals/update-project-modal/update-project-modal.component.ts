import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap';
import {ProjectService} from '../../services/project/project.service';
import {minimumDateValidator} from '../../validators/minimum-date-validator';
import {CreateProjectModel} from '../../models/createProject.model';
import {ProjectModel} from '../../models/project.model';
import {DateFormatterService} from '../../services/dateFormatter/dateFormatter.service';
import {siteUrlValidator} from '../../validators/siteUrl-validator';

@Component({
  selector: 'app-update-project-modal',
  templateUrl: './update-project-modal.component.html',
  styleUrls: ['./update-project-modal.component.css']
})
export class UpdateProjectModalComponent implements OnInit {

  public project: ProjectModel;

  errorOccurred: boolean;
  errorMessage: string;

  private form: FormGroup;

  constructor(public modalRef: BsModalRef,
              public projectService: ProjectService,
              private formBuilder: FormBuilder,
              private dateFormatterService: DateFormatterService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      title: [this.project.title, [
        Validators.required,
        Validators.maxLength(100)
      ]],
      description: [this.project.description, [
        Validators.maxLength(1000)
      ]],
      since: [this.dateFormatterService.formatDate(this.project.since), [
        Validators.required,
        minimumDateValidator
      ]],
      until: [this.dateFormatterService.formatDate(this.project.until), [
        Validators.required,
        minimumDateValidator
      ]],
      siteString: [this.project.sites.toString().split(',').join(',\n'), [
        siteUrlValidator
      ]],
      isExternal: [this.project.isExternal, []],
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    let sites: string[] = [];
    if (this.form.value.siteString) {
      sites = this.form.value.siteString.split(',');
      sites = sites.map(url => this.formatUrl(url));
    }
    if (!this.form.value.isExternal) {
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

  formatUrl(url: string) {
    url = url.trim();
    console.log('Formatting URL: ' + url);
    if (!(url.startsWith('http://') || url.startsWith('https://'))) {
      url = 'https://' + url;
    }
    console.log('Formatting resulted in: ' + url);
    return url;
  }

  handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }

}
