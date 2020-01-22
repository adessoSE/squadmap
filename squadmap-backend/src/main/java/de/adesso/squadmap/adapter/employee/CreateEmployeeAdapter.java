package de.adesso.squadmap.adapter.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeAdapter implements CreateEmployeePort {

    private EmployeeRepository employeeRepository;

    public CreateEmployeeAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public long createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }
        return employeeRepository.save(employee).getEmployeeId();
    }
}
