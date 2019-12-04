package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateEmployeeService implements UpdateEmployeeUseCase {


    @Override
    public void updateEmployee(UpdateEmployeeCommand command, Long employeeId) {

    }
}
