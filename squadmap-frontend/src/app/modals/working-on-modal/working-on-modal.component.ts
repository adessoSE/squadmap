import {Component, OnInit, ViewChild} from '@angular/core';
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
})
export class WorkingOnModalComponent implements OnInit {
  @ViewChild(NgForm, {static: true}) employeeForm: NgForm;
  public onClose: Subject<boolean>;
  private workingOnEmployee: WorkingOnEmployeeModel;
  private workingOnProject: WorkingOnProjectModel;
  private since: string;
  private until: string;
  private projectId: number;
  private employeeId: number;
  private workingOnId: number;
  private workload: number;
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
    if (!this.isNew) {
      this.isNew = false;
    }
  }


  onSubmit() {
    if (this.projectId) {
      this.employeeId = this.workingOnEmployee.employee.employeeId;
      this.workingOnId = this.workingOnEmployee.workingOnId;
    } else if (this.workingOnId) {
      this.projectId = this.workingOnProject.project.projectId;
      this.workingOnId = this.workingOnProject.workingOnId;
    }
    this.since = this.employeeForm.value.since;
    this.until = this.employeeForm.value.until;
    if (this.isNew) {
      const newWorkingOn = new CreateWorkingOnModel(
        this.edgeData.from,
        this.edgeData.to,
        this.employeeForm.value.since,
        this.employeeForm.value.until,
        +this.employeeForm.value.workload);
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
        this.employeeForm.value.since,
        this.employeeForm.value.until,
        +this.employeeForm.value.workload).subscribe(() => {
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
    location.reload();
  }
}
