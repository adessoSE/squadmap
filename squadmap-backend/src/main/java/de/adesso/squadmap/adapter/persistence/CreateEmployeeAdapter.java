package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@PersistenceAdapter
@RequiredArgsConstructor
class CreateEmployeeAdapter implements CreateEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> mapper;

    @Override
    public long createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }
        return employeeRepository.save(mapper.mapToNeo4JEntity(employee)).getEmployeeId();
    }
}
