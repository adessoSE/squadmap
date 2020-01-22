package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeService implements CreateEmployeeUseCase {

    private final CreateEmployeePort createEmployeePort;
    private final Mapper<CreateEmployeeCommand, Employee> employeeMapper;


    public CreateEmployeeService(CreateEmployeePort createEmployeePort, Mapper<CreateEmployeeCommand, Employee> employeeMapper) {
        this.createEmployeePort = createEmployeePort;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Long createEmployee(CreateEmployeeCommand command) {
        return createEmployeePort.createEmployee(employeeMapper.map(command));
    }
}
