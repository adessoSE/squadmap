import { Component, OnInit } from '@angular/core';
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
  // private existingEmployees: WorkingOnEmployeeModel[];
  private projectId: number;
  searchText: string;

  constructor(private modalRef: BsModalRef,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    // TODO filter here and display only not already existingEmployees in List
  }

  onAddEmployee(employee: EmployeeModel) {
    this.workingOnService.createWorkingOn(
      new CreateWorkingOnModel(employee.employeeId, this.projectId, new Date(), new Date())).subscribe(() => {
      this.modalRef.hide();
      location.reload();
    });
  }
}
