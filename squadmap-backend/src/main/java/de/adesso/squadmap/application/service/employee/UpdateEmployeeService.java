package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateEmployeeService implements UpdateEmployeeUseCase {

    private final UpdateEmployeePort updateEmployeePort;

    @Override
    public void updateEmployee(UpdateEmployeeCommand command, Long employeeId) {
        updateEmployeePort.updateEmployee(command.toEmployee(employeeId));
    }
}
