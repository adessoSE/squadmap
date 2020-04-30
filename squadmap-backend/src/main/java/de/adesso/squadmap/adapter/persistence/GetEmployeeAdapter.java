package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GetEmployeeAdapter implements GetEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;

    @Override
    public Employee getEmployee(Long employeeId) {
        return employeePersistenceMapper.mapToDomainEntity(employeeRepository.findById(employeeId, 0)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId)));
    }
}
