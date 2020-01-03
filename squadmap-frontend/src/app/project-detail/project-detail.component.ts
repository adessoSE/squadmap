import {Component, OnInit, TemplateRef} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ActivatedRoute} from '@angular/router';
import {ProjectService} from '../service/project.service';
import {EmployeeService} from '../service/employee.service';
import {EmployeeModel} from '../models/employee.model';
import {WorkingOnService} from '../service/workingOn.service';
import {WorkingOnEmployeeModel} from '../models/workingOnEmployee.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';

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
              private modalService: BsModalService) { }

  ngOnInit() {
    this.isSearching = false;
    this.project = new ProjectModel(0,  '', '', null, null, false, []); // vermeidet exceptions beim Aufbau der view
    this.refreshProject();
    this.searchText = '';
  }

  onSearchForEmployee() {
    if (this.searchText.length === 0) {
      this.isSearching = false;
    } else {
      this.isSearching = true;
      this.filteredEmployees = [];
      this.filteredEmployees = this.employeeService.getCurrentEmployees();
    }
  }

  onAddEmployee(employee: EmployeeModel) {
    this.workingOnService.addEmployeeToProject(employee.employeeId, this.project.projectId).subscribe(workingOnId => {
      this.project.employees.push(new WorkingOnEmployeeModel( +workingOnId, employee, new Date(), new Date() ));
      this.isSearching = false;
      this.modalRef.hide();
    });
  }

  onAddEmployeeModal(addEmployeeModal: TemplateRef<any>) {
    this.filteredEmployees = this.employeeService.getCurrentEmployees();
    this.modalRef = this.modalService.show(addEmployeeModal);
  }

  onDelete(workingOn: WorkingOnEmployeeModel) {
    console.log(workingOn.workingOnId);
    this.workingOnService.removeEmployeeFromProject(workingOn.workingOnId).subscribe(() => {
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
        res.projectId, res.title, res.description, res.since, res.until, res.isExternal, res.employees
      );
    });
  }

  onEdit(employee: WorkingOnEmployeeModel) {
    // TODO
  }
}
