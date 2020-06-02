import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {EmployeeModel} from '../../models/employee.model';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';

@Component({
  selector: 'app-add-employee-modal',
  templateUrl: './add-employee-modal.component.html',
})
export class AddEmployeeModalComponent implements OnInit {
  private employees: EmployeeModel[];
  private projectId: number;
  private searchText: string;
  private errorMessage: string;
  private errorOccurred: boolean;

  constructor(private modalRef: BsModalRef,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.errorMessage = '';
  }

  onAddEmployee(employee: EmployeeModel, since: Date, until: Date, workload: number) {
    this.workingOnService.createWorkingOn(
      new CreateWorkingOnModel(employee.employeeId, this.projectId, since, until, workload))
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
