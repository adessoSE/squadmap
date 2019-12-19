import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ActivatedRoute} from '@angular/router';
import {ProjectService} from '../service/project.service';
import {EmployeeService} from '../service/employee.service';
import {EmployeeModel} from '../models/employee.model';
import {WorkingOnService} from '../service/workingOn.service';
import {WorkingOnEmployeeModel} from '../models/workingOnEmployee.model';

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

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private employeeService: EmployeeService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.isSearching = false;
    this.project = new ProjectModel(0,  '', '', null, null, false, []);
    this.refreshProject();
  }


  onSearchForEmployee() {
    if (this.searchText.length === 0) {
      this.isSearching = false;
    } else {
      this.isSearching = true;
      this.filteredEmployees = this.employeeService.getCurrentEmployees();
    }
  }

  onAddEmployee(employee: EmployeeModel) {
    this.workingOnService.addEmployeeToProject(employee, this.project).subscribe(() => {
      this.refreshProject();
      this.isSearching = false;
    });

  }

  onDelete(workingOn: WorkingOnEmployeeModel) {
    this.workingOnService.removeEmployeeFromProject(workingOn.workingOnId).subscribe(() => {
        this.refreshProject();
    });
  }

  refreshProject() {
    this.projectService.getProject( this.route.snapshot.params.id).subscribe( res => {
      this.project = new ProjectModel(
        res.projectId, res.title, res.description, res.since, res.until, res.isExternal, res.employees
      );
    });
  }

  onEdit(employee: WorkingOnEmployeeModel) {
    // TODO
  }
}
