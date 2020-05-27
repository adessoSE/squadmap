package de.adesso.squadmap.application.port.driven.employee;

import de.adesso.squadmap.domain.Employee;

public interface CreateEmployeePort {

    long createEmployee(Employee employee);
}
