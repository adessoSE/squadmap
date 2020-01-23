package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
class ListEmployeeAdapter implements ListEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;

    @Override
    public List<Employee> listEmployees() {
        List<Employee> employees = new ArrayList<>(Collections.emptyList());
        employeeRepository.findAll().forEach(employeeDto -> employees.add(mapper.mapToDomainEntity(employeeDto)));
        return employees;
    }
}
