package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import de.adesso.squadmap.utility.UpdateCommandToEmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateEmployeeService implements UpdateEmployeeUseCase {

    private final UpdateEmployeePort updateEmployeePort;
    private final UpdateCommandToEmployeeMapper mapper;

    public UpdateEmployeeService(UpdateEmployeePort updateEmployeePort, UpdateCommandToEmployeeMapper mapper) {
        this.updateEmployeePort = updateEmployeePort;
        this.mapper = mapper;
    }

    @Override
    public void updateEmployee(UpdateEmployeeCommand command, Long employeeId) {
        Employee employee = mapper.map(command);
        employee.setEmployeeId(employeeId);
        updateEmployeePort.updateEmployee(employee);
    }
}
