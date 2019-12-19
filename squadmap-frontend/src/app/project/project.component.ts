import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {ProjectService} from '../service/project.service';
import {Router} from '@angular/router';
import {CreateProjectModel} from '../models/createProject.model';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css'],
})
export class ProjectComponent implements OnInit {

  private projectList: ProjectModel[] = [];
  public searchText: string;
  public checkedOldProjects: boolean;
  public checkedExternalProjects: boolean;


  constructor(private projectService: ProjectService, private router: Router) { }

  ngOnInit() {
    this.projectList = this.projectService.getProjects();
  }

  onOpenProject(project: ProjectModel) {
    this.router.navigate(['/project/' + project.projectId]);
  }

  onDelete(project: ProjectModel) {
    this.projectService.deleteProject(project).subscribe(res => {
      console.log(res);
      this.projectList = this.projectService.getProjects();
    });
  }

  onUpdate(project: ProjectModel) {
    // TODO open here edit dialogue of project and then call:
    // let newProject: ProjectModel;
    // let id: number;
    // this.projectService.updateProject(newProject, id);
  }

  onAddProject() {
    const dummyProject = new CreateProjectModel('Test-title', 'description', new Date(), new Date(), false);
    this.projectService.addProject(dummyProject).subscribe(res => {
      console.log(res);
      this.projectList = this.projectService.getProjects();
    });
  }
}
