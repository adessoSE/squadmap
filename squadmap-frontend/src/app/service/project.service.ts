import {Injectable} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {CreateProjectModel} from '../models/createProject.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private projects: ProjectModel[] = [];

  constructor(public http: HttpClient) {}

  getProjects() {
    this.projects = [];
    this.http.get<ProjectModel[]>('http://localhost:8080/project/all').pipe(map(res => {
      Object.values(res).map(receivedData => {
        this.projects.push(new ProjectModel(
          receivedData.projectId,
          receivedData.title,
          receivedData.description,
          new Date(receivedData.since),
          new Date(receivedData.until),
          receivedData.isExternal,
          receivedData.employees
        ));
      });
    })).subscribe(() => {
      return this.projects;
    });
    return this.projects;
  }

  getProject(id: number): ProjectModel {
    let foundProject: ProjectModel;
    this.projects.filter(project => {
      if (project.projectId === id) {
        foundProject = project;
      }
    });
    return foundProject;
  }

  deleteProject(project: ProjectModel) {
    return this.http.delete('http://localhost:8080/project/delete/' + project.projectId);
  }

  updateProject(newProject: ProjectModel, projectId: number) {
    return this.http.put('http://localhost:8080/project/update/' + projectId, {
      title: newProject.title,
      description: newProject.description,
      since: newProject.since,
      until: newProject.until,
      isExternal: newProject.isExternal
    });
  }

  addProject(dummyProject: CreateProjectModel) {
    return this.http.post('http://localhost:8080/project/create', {
      title: dummyProject.title,
      description: dummyProject.description,
      since: dummyProject.since,
      until: dummyProject.until,
      isExternal: dummyProject.isExternal
    });
  }
}
