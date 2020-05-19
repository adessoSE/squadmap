import {Injectable} from '@angular/core';
import {ProjectModel} from '../../models/project.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {CreateProjectModel} from '../../models/createProject.model';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  public projects: ProjectModel[] = [];

  constructor(public http: HttpClient) {}

  getProjects() {
    this.projects = [];
    return this.http.get<ProjectModel[]>(environment.base_api_url + '/project/all').pipe(map(res => {
      Object.values(res).forEach(receivedData => {
        this.projects.push(new ProjectModel(
          receivedData.projectId,
          receivedData.title,
          receivedData.description,
          new Date(receivedData.since),
          new Date(receivedData.until),
          receivedData.isExternal,
          receivedData.sites,
          receivedData.employees
        ));
      });
    }));
  }

  getProject(id: number) {
    return this.http.get<ProjectModel>(environment.base_api_url + '/project/' + id).pipe(map(res => {
      res.since = new Date(res.since);
      res.until = new Date(res.until);
      for (const workingOn of res.employees) {
        workingOn.since = new Date(workingOn.since);
        workingOn.until = new Date(workingOn.until);
      }
      return res;
    }));
  }

  deleteProject(projectId: number) {
    return this.http.delete(environment.base_api_url + '/project/delete/' + projectId);
  }

  updateProject(newProject: CreateProjectModel, projectId: number) {
    return this.http.put(environment.base_api_url + '/project/update/' + projectId, {
      title: newProject.title,
      description: newProject.description,
      since: newProject.since,
      until: newProject.until,
      isExternal: newProject.isExternal,
      sites: newProject.sites,
    });
  }

  addProject(dummyProject: CreateProjectModel) {
    return this.http.post(environment.base_api_url + '/project/create', {
      title: dummyProject.title,
      description: dummyProject.description,
      since: dummyProject.since,
      until: dummyProject.until,
      isExternal: dummyProject.isExternal,
      sites: dummyProject.sites,
    });
  }
}
