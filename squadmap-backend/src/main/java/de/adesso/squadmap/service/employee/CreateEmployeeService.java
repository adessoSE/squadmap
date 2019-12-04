package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateEmployeeService implements CreateEmployeeUseCase {

    private final EmployeeRepository employeeRepository;


    public CreateEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Long createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.getIsExternal());
        return employeeRepository.save(employee).getEmployeeId();
    }
}
