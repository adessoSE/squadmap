package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.domain.mapper.EmployeeDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
