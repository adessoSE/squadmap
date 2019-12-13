import {Component, OnInit, TemplateRef} from '@angular/core';
import {BsDropdownConfig, BsModalRef, BsModalService} from 'ngx-bootstrap';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [{provide: BsDropdownConfig, useValue: {isAnimated: true, autoClose: true}}]
})
export class NavigationComponent implements OnInit {
  isCollapsed = true;
  modalRef: BsModalRef;
  employeeForm: FormGroup;

  constructor(private modalService: BsModalService, private formbuilder: FormBuilder) {
    this.createEmployeeForm();
  }

  private createEmployeeForm() {
    this.employeeForm = this.formbuilder.group({
        title: '',
        description: '',
        since: new Date(),
        until: new Date(),
        isExternal: false,
      }
    );
  }

  addEmployeeModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  addProjectModal(template: TemplateRef<any>) {
  }

  ngOnInit() {
  }

}
