package de.adesso.squadmap.application.port.driven.employee;

import de.adesso.squadmap.domain.Employee;

import java.util.List;

public interface ListEmployeePort {

    List<Employee> listEmployees();
}
