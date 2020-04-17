import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {EmployeeService} from '../../../services/employee/employee.service';
import {EmployeeModel} from '../../../models/employee.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {EmployeeModalComponent} from '../../../modals/employee-modal/employee-modal.component';
import {CreateEmployeeModalComponent} from "../../../modals/create-employee-modal/create-employee-modal.component";

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  public employees: EmployeeModel[];
  public searchText: string;
  public hideExternal: boolean;
  private modalRef: BsModalRef;

  constructor(private employeeService: EmployeeService,
              private router: Router,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.hideExternal = false;
    this.searchText = '';
    this.employeeService.getEmployees().subscribe(() => {
      this.employees = this.employeeService.employees;
    });
  }

  onOpenEmployeeProfile(employee: EmployeeModel) {
    this.router.navigate(['/employee/' + employee.employeeId]);
  }

  onAddEmployee() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
    };
    this.modalRef = this.modalService.show(CreateEmployeeModalComponent, config);
  }

  onDelete(employee: EmployeeModel) {
    this.employeeService.deleteEmployee(employee.employeeId).subscribe(() => {
      this.employeeService.getEmployees().subscribe(() => {
        this.employees = this.employeeService.employees;
      });
    });
  }

  onEdit(employee: EmployeeModel) {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        employee,
        header: 'Update'
      }
    };
    this.modalRef = this.modalService.show(EmployeeModalComponent, config);
  }


}
