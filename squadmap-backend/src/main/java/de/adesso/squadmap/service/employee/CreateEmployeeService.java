package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeService implements CreateEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final Mapper<CreateEmployeeCommand, Employee> employeeMapper;


    public CreateEmployeeService(EmployeeRepository employeeRepository, Mapper<CreateEmployeeCommand, Employee> employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Long createEmployee(CreateEmployeeCommand command) {
        Employee employee = employeeMapper.map(command);
        return employeeRepository.save(employee).getEmployeeId();
    }
}
