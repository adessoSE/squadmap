package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeService implements CreateEmployeeUseCase {

    @Override
    public void createEmployee(CreateProjectCommand command) {

    }
}
