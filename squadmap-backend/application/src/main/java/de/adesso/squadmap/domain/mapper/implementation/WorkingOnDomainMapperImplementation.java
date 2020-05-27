package de.adesso.squadmap.domain.mapper.implementation;

import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.mapper.WorkingOnDomainMapper;
import org.springframework.stereotype.Component;

@Component
class WorkingOnDomainMapperImplementation implements WorkingOnDomainMapper {

    public WorkingOn mapToDomainEntity(CreateWorkingOnCommand command, Employee employee, Project project) {
        return  WorkingOn.builder()
                .workingOnId(null)
                .since(command.getSince())
                .until(command.getUntil())
                .workload(command.getWorkload())
                .employee(employee)
                .project(project)
                .build();
    }

    public WorkingOn mapToDomainEntity(UpdateWorkingOnCommand command, long workingOnId, Employee employee, Project project) {
        return WorkingOn.builder()
                .workingOnId(workingOnId)
                .since(command.getSince())
                .until(command.getUntil())
                .workload(command.getWorkload())
                .employee(employee)
                .project(project)
                .build();
    }
}
