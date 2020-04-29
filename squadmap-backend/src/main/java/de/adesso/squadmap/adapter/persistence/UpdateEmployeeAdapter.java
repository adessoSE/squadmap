package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class UpdateEmployeeAdapter implements UpdateEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;

    @Override
    public void updateEmployee(Employee employee) {
        EmployeeNeo4JEntity existingEmployee = employeeRepository.findById(employee.getEmployeeId(), 0)
                .orElseThrow(() -> new EmployeeNotFoundException(employee.getEmployeeId()));
        if (employeeRepository.existsByEmail(employee.getEmail()) && !employee.getEmail().equals(existingEmployee.getEmail())) {
            throw new EmployeeAlreadyExistsException(employee.getEmail());
        }
        employeeRepository.save(employeePersistenceMapper.mapToNeo4JEntity(employee), 0);
    }
}
