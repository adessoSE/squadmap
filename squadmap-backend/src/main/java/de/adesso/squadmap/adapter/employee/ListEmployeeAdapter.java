package de.adesso.squadmap.adapter.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ListEmployeeAdapter implements ListEmployeePort {

    private EmployeeRepository employeeRepository;

    public ListEmployeeAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> listEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
