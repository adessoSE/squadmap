package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateWorkingOnAdapter implements CreateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final WorkingOnPersistenceMapper mapper;

    @Override
    public long createWorkingOn(WorkingOn workingOn) {
        if (!employeeRepository.existsById(workingOn.getEmployee().getEmployeeId())) {
            throw new EmployeeNotFoundException();
        }
        if (!projectRepository.existsById(workingOn.getProject().getProjectId())) {
            throw new ProjectNotFoundException();
        }
        if (workingOnRepository.existsByEmployeeAndProject(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        return workingOnRepository.save(mapper.mapToNeo4JEntity(workingOn), 0).getWorkingOnId();
    }
}
