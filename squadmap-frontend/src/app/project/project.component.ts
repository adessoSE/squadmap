import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ProjectService} from '../service/project.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  private projectList: ProjectModel[] = [];
  public searchText: string;
  public checkedOldProjects: boolean;
  public checkedExternalProjects: boolean;


  constructor(private projectService: ProjectService) { }

  ngOnInit() {
    this.projectList = this.projectService.getProjects();
  }

}
