import {Pipe, PipeTransform} from '@angular/core';
import {ProjectModel} from '../../models/project.model';

export interface FilterSettings {
  searchText: string;
  checkedOldProjects: boolean;
  checkedExternalProjects: boolean;
}

@Pipe({
  name: 'filterProjects'
})
export class FilterProjectsPipe implements PipeTransform {
  transform(projectList: ProjectModel[], filter: FilterSettings): ProjectModel[] {
    if (!projectList) { return []; }
    if (!filter.searchText && !filter.checkedOldProjects && !filter.checkedExternalProjects) {
      return projectList;
    }
    if (filter.searchText && filter.searchText.length > 0) {
      projectList = projectList.filter(project => {
        if (project.title.toLowerCase().includes(filter.searchText)) {
          return project;
        }
      });
    }
    if (filter.checkedOldProjects) {
      projectList = projectList.filter(project => {
        const currentDate: Date = new Date();
        currentDate.setHours(0);
        if (!(project.until < currentDate)) {
          return project;
        }
      });
    }
    if (filter.checkedExternalProjects) {
      projectList = projectList.filter(project => {
            if (!project.isExternal) {
              return project;
            }
    });
    }
    return projectList;
  }
}
