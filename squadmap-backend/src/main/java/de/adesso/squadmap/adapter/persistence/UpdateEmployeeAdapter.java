package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdateEmployeeAdapter implements UpdateEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeePersistenceMapper mapper;

    @Override
    public void updateEmployee(Employee employee) {
        EmployeeNeo4JEntity existingEmployee = employeeRepository.findById(employee.getEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        if (employeeRepository.existsByEmail(employee.getEmail()) && !employee.getEmail().equals(existingEmployee.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }
        employeeRepository.save(mapper.mapToNeo4JEntity(employee, existingEmployee.getProjects()));
    }
}
