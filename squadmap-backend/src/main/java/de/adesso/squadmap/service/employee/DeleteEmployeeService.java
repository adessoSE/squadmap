package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driven.employee.DeleteEmployeePort;
import de.adesso.squadmap.port.driver.employee.delete.DeleteEmployeeUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteEmployeeService implements DeleteEmployeeUseCase {

    private final DeleteEmployeePort deleteEmployeePort;

    DeleteEmployeeService(DeleteEmployeePort deleteEmployeePort1){
        this.deleteEmployeePort = deleteEmployeePort1;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        deleteEmployeePort.deleteEmployee(employeeId);
    }
}
