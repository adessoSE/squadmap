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
        if (workingOnRepository.existsByEmployeeAndProject(workingOn.getEmployee().getEmployeeId(), workingOn.getProject().getProjectId())) {
            throw new WorkingOnAlreadyExistsException();
        }
        EmployeeNeo4JEntity employeeNeo4JEntity = employeeRepository.findById(workingOn.getEmployee().getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        ProjectNeo4JEntity projectNeo4JEntity = projectRepository.findById(workingOn.getProject().getProjectId())
                .orElseThrow(ProjectNotFoundException::new);
        return workingOnRepository.save(
                mapper.mapToNeo4JEntity(
                        workingOn,
                        employeeNeo4JEntity,
                        projectNeo4JEntity))
                .getWorkingOnId();
    }
}
