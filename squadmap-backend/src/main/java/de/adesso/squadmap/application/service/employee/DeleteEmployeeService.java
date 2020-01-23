package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.DeleteEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteEmployeeService implements DeleteEmployeeUseCase {

    private final DeleteEmployeePort deleteEmployeePort;

    @Override
    public void deleteEmployee(Long employeeId) {
        deleteEmployeePort.deleteEmployee(employeeId);
    }
}
