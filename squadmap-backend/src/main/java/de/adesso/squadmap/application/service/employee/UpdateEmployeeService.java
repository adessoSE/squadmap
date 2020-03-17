package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UpdateEmployeeService implements UpdateEmployeeUseCase {

    private final UpdateEmployeePort updateEmployeePort;
    private final EmployeeDomainMapper employeeMapper;

    @Override
    @Transactional
    public void updateEmployee(UpdateEmployeeCommand command, Long employeeId) {
        updateEmployeePort.updateEmployee(employeeMapper.mapToDomainEntity(command, employeeId));
    }
}
