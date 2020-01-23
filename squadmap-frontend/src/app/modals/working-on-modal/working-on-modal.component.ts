import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {WorkingOnEmployeeModel} from '../../models/workingOnEmployee.model';
import {DateFormatterService} from '../../services/dateFormatter/dateFormatter.service';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {WorkingOnProjectModel} from '../../models/workingOnProject.model';

@Component({
  selector: 'app-working-on-modal',
  templateUrl: './working-on-modal.component.html',
  styleUrls: ['./working-on-modal.component.css']
})
export class WorkingOnModalComponent implements OnInit {

  private workingOnEmployee: WorkingOnEmployeeModel;
  private workingOnProject: WorkingOnProjectModel;
  public since: string;
  public until: string;
  public projectId: number;
  public employeeId: number;
  public workingOnId: number;
  public workload: number;
  private errorMessage: string;
  private errorOccurred: boolean;

  constructor(private modalRef: BsModalRef,
              private dateFormatter: DateFormatterService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.errorMessage = '';
    if (this.projectId) {
      this.since = this.dateFormatter.formatDate(this.workingOnEmployee.since);
      this.until = this.dateFormatter.formatDate(this.workingOnEmployee.until);
    } else {
      this.since = this.dateFormatter.formatDate(this.workingOnProject.since);
      this.until = this.dateFormatter.formatDate(this.workingOnProject.until);
    }
  }


  onSubmit(employeeForm: NgForm) {
    if (this.projectId) {
      this.employeeId = this.workingOnEmployee.employee.employeeId;
      this.workingOnId = this.workingOnEmployee.workingOnId;
    } else {
      this.projectId = this.workingOnProject.project.projectId;
      this.workingOnId = this.workingOnProject.workingOnId;
    }
    this.since = employeeForm.value.since;
    this.until = employeeForm.value.until;
    this.workingOnService.updateWorkingOn(
      this.workingOnId,
      this.employeeId,
      this.projectId,
      employeeForm.value.since,
      employeeForm.value.until,
      employeeForm.value.workload).subscribe(() => {
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
