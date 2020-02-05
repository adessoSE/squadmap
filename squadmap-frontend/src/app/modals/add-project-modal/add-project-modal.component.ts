import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {WorkingOnService} from '../../services/workingOn/workingOn.service';
import {ProjectModel} from '../../models/project.model';
import {CreateWorkingOnModel} from '../../models/createWorkingOn.model';

@Component({
  selector: 'app-add-project-modal',
  templateUrl: './add-project-modal.component.html',
  styleUrls: ['./add-project-modal.component.css']
})
export class AddProjectModalComponent implements OnInit {
  private allProjects: ProjectModel[];
  private employeeId: number;
  searchText: string;
  private errorMessage: string;
  private errorOccurred: boolean;

  constructor(private modalRef: BsModalRef,
              private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.errorMessage = '';
    // TODO filter here and display only not already existingProjects in List
  }

  onAddProject(project: ProjectModel, input: HTMLInputElement) {
    this.workingOnService.createWorkingOn(
      new CreateWorkingOnModel(this.employeeId, project.projectId, new Date(), new Date(), +input.value)).subscribe(() => {
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
