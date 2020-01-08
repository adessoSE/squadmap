import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {WorkingOnEmployeeModel} from '../models/workingOnEmployee.model';
import {DateFormatterService} from '../service/dateFormatter.service';
import {WorkingOnService} from '../service/workingOn.service';
import {WorkingOnProjectModel} from '../models/workingOnProject.model';

@Component({
  selector: 'app-working-on-modal',
  templateUrl: './working-on-modal.component.html',
  styleUrls: ['./working-on-modal.component.css']
})
export class WorkingOnModalComponent implements OnInit {

  private workingOnEmployee: WorkingOnEmployeeModel;
  private workingOnProject: WorkingOnProjectModel;
  private since: string;
  private until: string;
  private projectId: number;
  private employeeId: number;

  constructor(private modalRef: BsModalRef,
              private dateFormatter: DateFormatterService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
   if (this.projectId) {
      this.since = this.dateFormatter.formatDate(this.workingOnEmployee.since);
      this.until = this.dateFormatter.formatDate(this.workingOnEmployee.until);
    } else {
      this.since = this.dateFormatter.formatDate(this.workingOnProject.since);
      this.until = this.dateFormatter.formatDate(this.workingOnProject.until);
    }
  }

  onSubmit(employeeForm: NgForm) {
    let workingOnId: number;
    let employeeId: number;
    let projectId: number;
    if (this.projectId) {
      employeeId = this.workingOnEmployee.employee.employeeId;
      projectId = this.projectId;
      workingOnId = this.workingOnEmployee.workingOnId;
    } else {
      employeeId = this.employeeId;
      projectId = this.workingOnProject.project.projectId;
      workingOnId = this.workingOnProject.workingOnId;
    }
    this.workingOnService.updateWorkingOn(
      workingOnId,
      employeeId,
      projectId,
      employeeForm.value.since,
      employeeForm.value.until).subscribe(res => {
        this.modalRef.hide();
        location.reload();
    });
  }
}
