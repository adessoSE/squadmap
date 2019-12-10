package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.utility.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListEmployeeService implements ListEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public ListEmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper){
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<GetEmployeeResponse> listEmployees() {
        List<GetEmployeeResponse> responses = new ArrayList<>();
        employeeRepository.findAll().forEach(employee ->
            responses.add(employeeMapper.mapEmployeeToEmployeeResponse(employee)));
        return responses;
    }
}
