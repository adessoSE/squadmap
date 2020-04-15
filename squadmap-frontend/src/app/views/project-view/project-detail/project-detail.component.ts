import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../../../models/project.model';
import {ActivatedRoute, Router} from '@angular/router';
import {ProjectService} from '../../../services/project/project.service';
import {EmployeeService} from '../../../services/employee/employee.service';
import {EmployeeModel} from '../../../models/employee.model';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {WorkingOnEmployeeModel} from '../../../models/workingOnEmployee.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {AddEmployeeModalComponent} from '../../../modals/add-employee-modal/add-employee-modal.component';
import {UpdateWorkingOnEmployeeModalComponent} from "../../../modals/update-working-on-employee-modal/update-working-on-employee-modal.component";
import {UpdateProjectModalComponent} from "../../../modals/update-project-modal/update-project-modal.component";

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {

  private project: ProjectModel;
  private filteredEmployees: EmployeeModel[];
  searchText: string;
  isSearching: boolean;
  modalRef: BsModalRef;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private employeeService: EmployeeService,
              private workingOnService: WorkingOnService,
              private modalService: BsModalService,
              private router: Router) { }

  ngOnInit() {
    this.isSearching = false;
    this.project = new ProjectModel(0,  '', '', new Date(), new Date(), false, [], []); // vermeidet exceptions beim Aufbau der view
    this.refreshProject();
    this.searchText = '';
  }

  onOpenAddEmployeeModal() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        employees: this.filteredEmployees,
        projectId: this.project.projectId
      },
      class: 'modal-lg'
    };
    this.modalRef = this.modalService.show(AddEmployeeModalComponent, config);
  }

  onDelete(workingOn: WorkingOnEmployeeModel) {
    this.workingOnService.deleteWorkingOn(workingOn.workingOnId).subscribe(() => {
        this.refreshProject();
    });
  }

  refreshProject() {
    this.projectService.getProject( this.route.snapshot.params.id).subscribe( res => {
      for (const employee of res.employees) {
        employee.since = new Date(employee.since);
        employee.until = new Date(employee.until);
      }
      this.project = new ProjectModel(
        res.projectId, res.title, res.description, res.since, res.until, res.isExternal, res.sites, res.employees
      );
      this.updateFilteredEmployees();
    }, error => {
      this.router.navigate(['project']);
    });
  }

  onEdit(workingOnEmployee: WorkingOnEmployeeModel) {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        workingOnEmployee,
        projectId: this.project.projectId,
        workload: workingOnEmployee.workload
      }
    };
    this.modalRef = this.modalService.show(UpdateWorkingOnEmployeeModalComponent, config);
  }

  onOpenEmployeeDetail(employeeId: number) {
    this.router.navigate(['/employee/' + employeeId]);
  }

  onUpdate() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        project: this.project,
      }
    };
    this.modalRef = this.modalService.show(UpdateProjectModalComponent, config);
  }

  filterEmployees(allEmployees: EmployeeModel[], existingEmployees: WorkingOnEmployeeModel[]): EmployeeModel[] {
    const filteredEmployees = allEmployees.filter(employee => {
      let found = false;
      existingEmployees.forEach(elem => {
        if (employee.employeeId === elem.employee.employeeId) {
          found = true;
          return;
        }
      });
      if (!found) { return employee; }
    });
    return filteredEmployees;
  }

  private updateFilteredEmployees() {
    this.employeeService.getEmployees().subscribe(() => {
      this.filteredEmployees = this.employeeService.employees;
      this.filteredEmployees = this.filterEmployees(this.filteredEmployees, this.project.employees);
    });
  }
}
