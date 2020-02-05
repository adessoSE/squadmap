package de.adesso.squadmap.application.port.driven.employee;

import de.adesso.squadmap.application.domain.Employee;

public interface CreateEmployeePort {

    long createEmployee(Employee employee);
}
