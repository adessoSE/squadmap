import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {WorkingOnEmployeeModel} from '../../models/workingOnEmployee.model';
import {DateFormatterService} from '../../services/dateFormatter/dateFormatter.service';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {WorkingOnProjectModel} from '../../models/workingOnProject.model';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-working-on-modal',
  templateUrl: './working-on-modal.component.html',
  styleUrls: ['./working-on-modal.component.css']
})
export class WorkingOnModalComponent implements OnInit {
  public onClose: Subject<boolean>;

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
  private isNew: boolean;
  private edgeData;

  constructor(private modalRef: BsModalRef,
              private dateFormatter: DateFormatterService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.onClose = new Subject();
    this.errorMessage = '';
    if (!this.since) {
      this.since = this.dateFormatter.formatDate(new Date());
    }
    if (!this.until) {
      this.until = this.dateFormatter.formatDate(new Date());
    }
    if (this.projectId) {
      this.since = this.dateFormatter.formatDate(this.workingOnEmployee.since);
      this.until = this.dateFormatter.formatDate(this.workingOnEmployee.until);
    } else if (this.workingOnId) {
      this.since = this.dateFormatter.formatDate(this.workingOnProject.since);
      this.until = this.dateFormatter.formatDate(this.workingOnProject.until);
    }
  }


  onSubmit(employeeForm: NgForm) {
    if (this.projectId) {
      this.employeeId = this.workingOnEmployee.employee.employeeId;
      this.workingOnId = this.workingOnEmployee.workingOnId;
    } else if (this.workingOnId) {
      this.projectId = this.workingOnProject.project.projectId;
      this.workingOnId = this.workingOnProject.workingOnId;
    }
    this.since = employeeForm.value.since;
    this.until = employeeForm.value.until;
    if (this.isNew) {
      const newWorkingOn = new CreateWorkingOnModel(
        this.edgeData.from,
        this.edgeData.to,
        employeeForm.value.since,
        employeeForm.value.until,
        +employeeForm.value.workload);
      this.workingOnService.createWorkingOn(
        newWorkingOn).subscribe(() => {
        this.onConfirm();
      }, error => {
        this.handleError(error.error.message);
      });
    } else {
      this.workingOnService.updateWorkingOn(
        this.workingOnId,
        this.employeeId,
        this.projectId,
        employeeForm.value.since,
        employeeForm.value.until,
        +employeeForm.value.workload).subscribe(() => {
        this.onConfirm();
      }, error => {
        this.handleError(error.error.message);
      });
    }

  }

  private handleError(message: string) {
    this.errorOccurred = true;
    this.errorMessage = message;
    setTimeout(() => {
      this.errorOccurred = false;
    }, 10000);
  }

  public onConfirm(): void {
    this.onClose.next(true);
    this.modalRef.hide();
  }
}
