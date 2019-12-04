package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEmployeeService implements ListEmployeeUseCase {


    @Override
    public List<GetEmployeeResponse> listEmployees() {
        return null;
    }
}
