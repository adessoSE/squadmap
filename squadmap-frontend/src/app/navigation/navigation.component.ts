import {Component, OnInit} from '@angular/core';
import {BsDropdownConfig, BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ProjectModalComponent} from '../project-modal/project-modal.component';
import {EmployeeModalComponent} from '../employee-modal/employee-modal.component';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [{provide: BsDropdownConfig, useValue: {isAnimated: true, autoClose: true}}]
})
export class NavigationComponent implements OnInit {

  modalRef: BsModalRef;

  constructor(private modalService: BsModalService) { }

  ngOnInit() {
  }

  addEmployeeModal() {
    const initialState = {
      actionName: 'Create'
    };
    this.modalRef = this.modalService.show(EmployeeModalComponent, {initialState});
  }

  addProjectModal() {
    const initialState = {
      actionName: 'Create'
    };
    this.modalRef = this.modalService.show(ProjectModalComponent, {initialState});
  }
}
