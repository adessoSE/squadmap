package de.adesso.squadmap.adapter.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class GetEmployeeAdapter implements GetEmployeePort {

    private EmployeeRepository employeeRepository;

    public GetEmployeeAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }
}
