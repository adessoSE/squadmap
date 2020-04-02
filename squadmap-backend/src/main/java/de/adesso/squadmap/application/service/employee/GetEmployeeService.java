package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetEmployeeService implements GetEmployeeUseCase {

    private final GetEmployeePort getEmployeePort;

    @Override
    @Transactional
    public Employee getEmployee(Long employeeId) {
        return getEmployeePort.getEmployee(employeeId);
    }
}
