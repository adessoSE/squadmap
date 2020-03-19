package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class UpdateWorkingOnAdapter implements UpdateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> workingOnPersistenceMapper;

    @Override
    public void updateWorkingOn(WorkingOn workingOn) {
        if (!employeeRepository.existsById(workingOn.getEmployee().getEmployeeId())) {
            throw new EmployeeNotFoundException();
        }
        if (!projectRepository.existsById(workingOn.getProject().getProjectId())) {
            throw new ProjectNotFoundException();
        }
        if (!workingOnRepository.existsById(workingOn.getWorkingOnId())) {
            throw new WorkingOnNotFoundException();
        }
        workingOnRepository.save(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn), 0);
    }
}
