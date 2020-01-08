import {Component, OnInit, TemplateRef} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
import {ActivatedRoute, Router} from '@angular/router';
import {EmployeeService} from '../service/employee.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ProjectModel} from '../models/project.model';
import {ProjectService} from '../service/project.service';
import {WorkingOnProjectModel} from '../models/workingOnProject.model';
import {WorkingOnService} from '../service/workingOn.service';
import {WorkingOnModalComponent} from '../working-on-modal/working-on-modal.component';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css']
})
export class EmployeeDetailComponent implements OnInit {

  private employee: EmployeeModel;
  private allProjects: ProjectModel[];
  searchText: string;
  modalRef: BsModalRef;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private employeeService: EmployeeService,
              private modalService: BsModalService,
              private projectService: ProjectService,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.employee = new EmployeeModel(0, '', '', new Date(), '', '', false, []);
    this.employeeService.getEmployee(this.route.snapshot.params.id).subscribe(res => {
      this.employee = new EmployeeModel(
        res.employeeId,
        res.firstName,
        res.lastName, res.birthday,
        res.email,
        res.phone,
        res.isExternal,
        res.projects );
    });
    this.projectService.getProjects().subscribe(() => {
      this.allProjects = this.projectService.projects;
    });
  }

  onOpenAddProjectModal(addProjectModal: TemplateRef<any>) {
    this.modalRef = this.modalService.show(addProjectModal);
  }

  onAddProject(project: ProjectModel) {
    this.workingOnService.createWorkingOn(this.employee.employeeId, project.projectId).subscribe(workingOnId => {
      this.employee.projects.push(new WorkingOnProjectModel(+workingOnId, project, new Date(), new Date()));
      this.modalRef.hide();
    });
  }

  onDeleteProject(workingOnId: number) {
    this.workingOnService.deleteWorkingOn(workingOnId).subscribe(() => {
      this.employeeService.getEmployee(this.employee.employeeId).subscribe(employee => {
        this.employee = employee;
      });
    });
  }

  onEditProject(workingOnProject: WorkingOnProjectModel) {
    const initialState = {
      workingOnProject,
      employeeId: this.employee.employeeId
    };
    this.modalRef = this.modalService.show(WorkingOnModalComponent, {initialState});
  }

  onOpenProjectDetail(projectId: number) {
    this.router.navigate(['/project/' + projectId]);
  }
}
