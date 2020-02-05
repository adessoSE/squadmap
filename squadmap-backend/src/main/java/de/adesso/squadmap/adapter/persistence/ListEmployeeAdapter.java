package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
class ListEmployeeAdapter implements ListEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeePersistenceMapper mapper;

    @Override
    public List<Employee> listEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
