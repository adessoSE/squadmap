package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeService implements GetEmployeeUseCase {


    @Override
    public GetEmployeeResponse getEmployee(Long employeeId) {
        return null;
    }
}
