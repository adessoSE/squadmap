import {Pipe, PipeTransform} from '@angular/core';
import {ProjectModel} from '../models/project.model';

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
    if (!filter.searchText) {
      if (!filter.checkedOldProjects) {
        if (!filter.checkedExternalProjects) {
          return projectList;
        }
      }
    }
    return projectList.filter(project => {
      if (filter.searchText) {
        if (project.title.toLowerCase().includes(filter.searchText)) {
          return project;
        }
      }
      if (filter.checkedOldProjects) {
        const aktuellesDatum: Date = new Date();
        aktuellesDatum.setHours(0);
        if (project.until < aktuellesDatum) {
          return project;
        }
      }
      if (filter.checkedExternalProjects) {
        if (project.isExternal) {
          return project;
        }
      }
    });
  }
}
