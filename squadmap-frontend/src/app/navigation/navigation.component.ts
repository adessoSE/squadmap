import {Component, OnInit} from '@angular/core';
import {BsDropdownConfig, BsModalRef, BsModalService} from 'ngx-bootstrap';
import {EmployeeModalComponent} from '../modals/employee-modal/employee-modal.component';
import {CreateProjectModalComponent} from "../modals/create-project-modal/create-project-modal.component";


@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  providers: [{provide: BsDropdownConfig, useValue: {isAnimated: true, autoClose: true}}]
})
export class NavigationComponent implements OnInit {

  modalRef: BsModalRef;

  constructor(private modalService: BsModalService) { }

  ngOnInit() {
  }

  addEmployeeModal() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true,
      initialState: {
        header: 'Create'
      }
    };
    this.modalRef = this.modalService.show(EmployeeModalComponent, config);
  }

  addProjectModal() {
    const config = {
      backdrop: true,
      ignoreBackdropClick: true
    };
    this.modalRef = this.modalService.show(CreateProjectModalComponent, config);
  }
}
