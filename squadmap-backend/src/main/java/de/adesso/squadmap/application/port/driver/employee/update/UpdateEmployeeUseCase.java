package de.adesso.squadmap.application.port.driver.employee.update;

public interface UpdateEmployeeUseCase {

    void updateEmployee(UpdateEmployeeCommand updateEmployeeCommand, Long employeeId);
}
