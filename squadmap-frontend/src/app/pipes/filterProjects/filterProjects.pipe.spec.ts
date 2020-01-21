import {FilterProjectsPipe, FilterSettings} from './filterProjects.pipe';
import {ProjectModel} from '../../models/project.model';

describe('FilterProjectPipe', () => {
  let pipe: FilterProjectsPipe;
  const date: Date = new Date();
  let dummyProjects: ProjectModel[];
  let filterSettings: FilterSettings;

  beforeEach(() => {
    pipe = new FilterProjectsPipe();
    dummyProjects = [
      {
        projectId: 1,
        title: 'squadmap',
        description: 'Description1',
        since: date,
        until: date,
        isExternal: false,
        employees: []
      },
      {
        projectId: 2,
        title: 'coderadar',
        description: 'Description2',
        since: date,
        until: date,
        isExternal: true,
        employees: []
      },
    ];
    filterSettings = {
      searchText: '',
      checkedOldProjects: false,
      checkedExternalProjects: false
    };
  });

  describe('Correct Inputs', () => {
    it('should filter projects only by short search text', () => {
      filterSettings.searchText = 'squad';
      const expectedProjects: ProjectModel[] = [
        {
          projectId: 1,
          title: 'squadmap',
          description: 'Description1',
          since: date,
          until: date,
          isExternal: false,
          employees: []
        },
      ];
      expect(JSON.stringify(pipe.transform(dummyProjects, filterSettings))).toEqual(JSON.stringify(expectedProjects));
    });

    it('should filter by hiding externals',  () => {
      filterSettings.checkedExternalProjects = true;
      const expectedProjects: ProjectModel[] = [
        {
          projectId: 1,
          title: 'squadmap',
          description: 'Description1',
          since: date,
          until: date,
          isExternal: false,
          employees: []
        },
      ];
      expect(JSON.stringify(pipe.transform(dummyProjects, filterSettings))).toEqual(JSON.stringify(expectedProjects));
    });

    it('should filter by hiding all old projects', () => {
      filterSettings.checkedOldProjects = true;
      const oldDummyProjectsState: ProjectModel[] = [
        {
          projectId: 1,
          title: 'squadmap',
          description: 'Description1',
          since: date,
          until: date,
          isExternal: false,
          employees: []
        },
        {
          projectId: 2,
          title: 'coderadar',
          description: 'Description2',
          since: date,
          until: date,
          isExternal: true,
          employees: []
        },
      ];
      dummyProjects.push(new ProjectModel(3,
        'blog',
        'noDescription',
        new Date('December 17, 1995 00:00:00'),
        new Date('Januar 17, 1996 00:00:00'),
        false,
        [])
      );
      expect(JSON.stringify(pipe.transform(dummyProjects, filterSettings))).toEqual(JSON.stringify(oldDummyProjectsState));
    });
  });
});
