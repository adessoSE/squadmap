package de.adesso.squadmap.port.driver.employee.update;

public interface UpdateEmployeeUseCase {

    void updateEmployee(UpdateEmployeeCommand command, Long employeeId);
}
