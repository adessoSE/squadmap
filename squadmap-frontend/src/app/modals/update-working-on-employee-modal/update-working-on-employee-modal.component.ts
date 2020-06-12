import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap";
import {WorkingOnService} from "../../services/workingOn/workingOn.service";
import {DateFormatterService} from "../../services/dateFormatter/dateFormatter.service";
import {WorkingOnEmployeeModel} from "../../models/workingOnEmployee.model";
import {minimumDateValidator} from "../../validators/minimum-date-validator";

@Component({
  selector: 'app-update-working-on-employee-modal',
  templateUrl: './update-working-on-employee-modal.component.html',
  styleUrls: ['./update-working-on-employee-modal.component.css']
})
export class UpdateWorkingOnEmployeeModalComponent implements OnInit {

  workingOnEmployee: WorkingOnEmployeeModel;
  private projectId: number;
  private workload: number;

  errorOccurred: boolean;
  errorMessage: string;

  private form: FormGroup;

  constructor(private modalRef: BsModalRef,
              private formBuilder: FormBuilder,
              public workingOnService: WorkingOnService,
              private dateFormatterService: DateFormatterService) { }

  ngOnInit() {
    this.errorMessage = '';
    this.form = this.formBuilder.group({
      since: [this.dateFormatterService.formatDate(this.workingOnEmployee.since),[
        Validators.required,
        minimumDateValidator
      ]],
      until: [this.dateFormatterService.formatDate(this.workingOnEmployee.until),[
        Validators.required,
        minimumDateValidator
      ]],
      workload: [this.workingOnEmployee.workload,[
        Validators.required,
        Validators.min(0),
        Validators.max(100),
      ]],
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.workingOnService.updateWorkingOn(
      this.workingOnEmployee.workingOnId,
      this.workingOnEmployee.employee.employeeId,
      this.projectId,
      this.form.value.since,
      this.form.value.until,
      +this.form.value.workload).subscribe(() => {
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
