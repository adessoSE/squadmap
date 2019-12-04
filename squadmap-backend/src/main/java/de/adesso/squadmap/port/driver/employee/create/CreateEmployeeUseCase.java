package de.adesso.squadmap.port.driver.employee.create;

import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;

public interface CreateEmployeeUseCase {
    //TODO void to fitting return value
    void createEmployee(CreateProjectCommand command);
}
