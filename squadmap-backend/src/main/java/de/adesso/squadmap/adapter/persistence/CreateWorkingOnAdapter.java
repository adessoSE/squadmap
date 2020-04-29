package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.domain.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CreateWorkingOnAdapter implements CreateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final PersistenceMapper<WorkingOn, WorkingOnNeo4JEntity> workingOnPersistenceMapper;

    @Override
    public long createWorkingOn(WorkingOn workingOn) {
        if (!employeeRepository.existsById(workingOn.getEmployee().getEmployeeId())) {
            throw new EmployeeNotFoundException(workingOn.getEmployee().getEmployeeId());
        }
        if (!projectRepository.existsById(workingOn.getProject().getProjectId())) {
            throw new ProjectNotFoundException(workingOn.getProject().getProjectId());
        }
        if (workingOnRepository.existsByEmployeeAndProject(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId())) {
            throw new WorkingOnAlreadyExistsException(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId());
        }
        return workingOnRepository.save(workingOnPersistenceMapper.mapToNeo4JEntity(workingOn), 0).getWorkingOnId();
    }
}
