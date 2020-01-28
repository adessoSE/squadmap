import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {EmployeeModel} from '../../models/employee.model';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';

@Component({
  selector: 'app-add-employee-modal',
  templateUrl: './add-employee-modal.component.html',
  styleUrls: ['./add-employee-modal.component.css']
})
export class AddEmployeeModalComponent implements OnInit {
  private allEmployees: EmployeeModel[];
  private projectId: number;
  searchText: string;
  private errorMessage: string;
  private errorOccurred: boolean;

  constructor(private modalRef: BsModalRef,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.errorMessage = '';
    // TODO filter here and display only not already existingEmployees in List
  }

  onAddEmployee(employee: EmployeeModel, since: HTMLInputElement, until: HTMLInputElement, workload: HTMLInputElement) {
    this.workingOnService.createWorkingOn(
      new CreateWorkingOnModel(employee.employeeId, this.projectId, since.valueAsDate, until.valueAsDate, +workload.value))
      .subscribe(() => {
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
