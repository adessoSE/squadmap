import {Injectable} from '@angular/core';
import {ProjectModel} from '../models/project.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private projects: ProjectModel[] = [];

  constructor(public http: HttpClient) {}

  getProjects() {
    this.projects = [];
    this.http.get<ProjectModel[]>('http://localhost:8080/project/all').pipe(map(res => {
      Object.values(res).map(recievedData => {
        this.projects.push(new ProjectModel(
          recievedData.projectId,
          recievedData.title,
          recievedData.description,
          new Date(recievedData.since),
          new Date(recievedData.until),
          recievedData.isExternal,
          recievedData.employees
        ));
      });
    })).subscribe(() => {
      return this.projects;
    });
    return this.projects;
  }
}
