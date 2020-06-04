package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.common.PersistenceAdapter;
import de.adesso.squadmap.domain.Employee;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PersistenceAdapter
@RequiredArgsConstructor
class ListEmployeeAdapter implements ListEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;

    @Override
    public List<Employee> listEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(employeePersistenceMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
