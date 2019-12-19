import { Component, OnInit } from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ActivatedRoute, Params} from '@angular/router';
import {ProjectService} from '../service/project.service';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {

  private project: ProjectModel;

  constructor(private route: ActivatedRoute, private projectService: ProjectService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.project = this.projectService.getProject(+params.id);
    });
  }

}
