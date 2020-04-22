import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {CreateWorkingOnModel} from "../../models/createWorkingOn.model";
import {minimumDateValidator} from "../../validators/minimum-date-validator";

@Component({
  selector: 'app-new-working-on-modal',
  templateUrl: './new-working-on-modal.component.html'
})
export class NewWorkingOnModalComponent implements OnInit {

  edgeData: any;

  errorOccurred: boolean;
  errorMessage: string;

  private form: FormGroup;

  constructor(private modalRef: BsModalRef,
              private formBuilder: FormBuilder,
              public workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.errorMessage = '';
    this.form = this.formBuilder.group({
      since: ['',[
        Validators.required,
        minimumDateValidator
      ]],
      until: ['',[
        Validators.required,
        minimumDateValidator
      ]],
      workload: ['',[
        Validators.required,
        Validators.min(0),
        Validators.max(100),
      ]],
    });
  }

  onSubmit() {
    const newWorkingOn = new CreateWorkingOnModel(
      this.edgeData.from,
      this.edgeData.to,
      this.form.value.since,
      this.form.value.until,
      +this.form.value.workload);
    this.workingOnService.createWorkingOn(
      newWorkingOn).subscribe(() => {
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
