import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from '../../../models/employee.model';
import {ActivatedRoute, Router} from '@angular/router';
import {EmployeeService} from '../../../services/employee/employee.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ProjectModel} from '../../../models/project.model';
import {ProjectService} from '../../../services/project/project.service';
import {WorkingOnProjectModel} from '../../../models/workingOnProject.model';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {WorkingOnModalComponent} from '../../../modals/working-on-modal/working-on-modal.component';
import {AddProjectModalComponent} from '../../../modals/add-project-modal/add-project-modal.component';
import {EmployeeModalComponent} from '../../../modals/employee-modal/employee-modal.component';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css']
})
export class EmployeeDetailComponent implements OnInit {

  private employee: EmployeeModel;
  private filteredProjects: ProjectModel[];
  searchText: string;
  modalRef: BsModalRef;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private employeeService: EmployeeService,
              private modalService: BsModalService,
              private projectService: ProjectService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.employee = new EmployeeModel(0, '', '', new Date(), '', '', false, '', []);
    this.employeeService.getEmployee(this.route.snapshot.params.id).subscribe(res => {
      this.employee = new EmployeeModel(
        res.employeeId,
        res.firstName,
        res.lastName, res.birthday,
        res.email,
        res.phone,
        res.isExternal,
        res.image,
        res.projects );
      this.updateFilteredProjects();
    }, error => {
      this.router.navigate(['employee']);
    });
  }

  onOpenAddProjectModal() {
    this.updateFilteredProjects();
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        allProjects: this.filteredProjects,
        employeeId: this.employee.employeeId
      },
      class: 'modal-lg'
    };
    this.modalRef = this.modalService.show(AddProjectModalComponent, config);
  }

  onDeleteProject(workingOnId: number) {
    this.workingOnService.deleteWorkingOn(workingOnId).subscribe(() => {
      this.employeeService.getEmployee(this.employee.employeeId).subscribe(employee => {
        this.employee = employee;
        this.updateFilteredProjects();
      });
    });
  }

  onEditProject(workingOnProject: WorkingOnProjectModel) {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        workingOnProject,
        employeeId: this.employee.employeeId,
        workload: workingOnProject.workload
      }
    };
    this.modalRef = this.modalService.show(WorkingOnModalComponent, config);
  }

  onOpenProjectDetail(projectId: number) {
    this.router.navigate(['/project/' + projectId]);
  }


  onUpdate() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        isNew: false,
        employee: this.employee,
        actionName: 'Update'
      }
    };
    this.modalRef = this.modalService.show(EmployeeModalComponent, config);
  }

  private filterProjects(allProjects: ProjectModel[], existingProjects: WorkingOnProjectModel[]) {
    const filteredProjects = allProjects.filter(project => {
      let found = false;
      existingProjects.forEach(pro => {
        if (project.projectId === pro.project.projectId) {
          found = true;
          return;
        }
      });
      if (!found) { return project; }
    });
    return filteredProjects;
  }
  private updateFilteredProjects() {
    this.projectService.getProjects().subscribe(() => {
      this.filteredProjects = this.projectService.projects;
      this.filteredProjects = this.filterProjects(this.filteredProjects, this.employee.projects);
    });
  }
}
