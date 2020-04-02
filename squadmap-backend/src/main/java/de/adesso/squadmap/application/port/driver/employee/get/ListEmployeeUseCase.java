package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;

import java.util.List;

public interface ListEmployeeUseCase {

    List<Employee> listEmployees();
}
