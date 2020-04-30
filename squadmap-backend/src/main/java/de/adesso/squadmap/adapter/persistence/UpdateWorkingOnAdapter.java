package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.exceptions.WorkingOnNotFoundException;
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
            throw new EmployeeNotFoundException(workingOn.getEmployee().getEmployeeId());
        }
        if (!projectRepository.existsById(workingOn.getProject().getProjectId())) {
            throw new ProjectNotFoundException(workingOn.getProject().getProjectId());
        }
        if (!workingOnRepository.existsById(workingOn.getWorkingOnId())) {
            throw new WorkingOnNotFoundException(workingOn.getWorkingOnId());
        }
        workingOnRepository.save(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn), 0);
    }
}
