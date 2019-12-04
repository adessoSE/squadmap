package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListEmployeeService implements ListEmployeeUseCase {

    private final EmployeeRepository employeeRepository;

    public ListEmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<GetEmployeeResponse> listEmployees() {
        List<GetEmployeeResponse> responses = new ArrayList<>();
        employeeRepository.findAll().forEach(employee ->
            responses.add(new GetEmployeeResponse(
                    employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getBirthday(),
                    employee.getEmail(),
                    employee.getPhone(),
                    employee.getIsExternal())));
        return responses;
    }
}
