package de.adesso.squadmap.adapter.employee;

import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driven.employee.DeleteEmployeePort;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteEmployeeAdapter implements DeleteEmployeePort {

    private EmployeeRepository employeeRepository;

    public DeleteEmployeeAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.deleteById(employeeId);
    }
}
