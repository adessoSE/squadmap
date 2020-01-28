package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.adapter.persistence.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.workingon.UpdateWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdateWorkingOnAdapter implements UpdateWorkingOnPort {

    private final WorkingOnRepository workingOnRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final WorkingOnPersistenceMapper mapper;

    @Override
    public void updateWorkingOn(WorkingOn workingOn) {
        if (!workingOnRepository.existsById(workingOn.getWorkingOnId())) {
            throw new WorkingOnNotFoundException();
        }
        EmployeeNeo4JEntity employeeNeo4JEntity = employeeRepository.findById(workingOn.getEmployee().getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        ProjectNeo4JEntity projectNeo4JEntity = projectRepository.findById(workingOn.getProject().getProjectId())
                .orElseThrow(ProjectNotFoundException::new);

        workingOnRepository.save(mapper.mapToNeo4JEntity(workingOn, employeeNeo4JEntity, projectNeo4JEntity));
    }
}
