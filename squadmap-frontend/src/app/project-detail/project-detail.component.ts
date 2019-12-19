import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ActivatedRoute} from '@angular/router';
import {ProjectService} from '../service/project.service';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {

  private project: ProjectModel;
  searchText: string;

  constructor(private route: ActivatedRoute, private projectService: ProjectService) { }

  ngOnInit() {
    this.project = new ProjectModel(0,  '', '', null, null, false, []);
    this.projectService.getProject( this.route.snapshot.params.id).subscribe( res => {
      this.project = new ProjectModel(
        res.projectId, res.title, res.description, res.since, res.until, res.isExternal, res.employees
      );
    });
  }

}
