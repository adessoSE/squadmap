package de.adesso.squadmap.adapter.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeAlreadyExistsException;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmployeeAdapter implements UpdateEmployeePort {

    private EmployeeRepository employeeRepository;

    public UpdateEmployeeAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        if (employeeRepository.existsByEmail(employee.getEmail()) && !employee.getEmail().equals(existingEmployee.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }
        employeeRepository.save(employee);
    }
}
