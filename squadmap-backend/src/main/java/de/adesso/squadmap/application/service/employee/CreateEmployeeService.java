package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class CreateEmployeeService implements CreateEmployeeUseCase {

    private final CreateEmployeePort createEmployeePort;
    private final EmployeeDomainMapper employeeDomainMapper;

    @Override
    @Transactional
    public Long createEmployee(CreateEmployeeCommand createEmployeeCommand) {
        return createEmployeePort.createEmployee(employeeDomainMapper.mapToDomainEntity(createEmployeeCommand));
    }
}
