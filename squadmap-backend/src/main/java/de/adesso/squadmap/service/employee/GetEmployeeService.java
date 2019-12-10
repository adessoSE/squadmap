package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.utility.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeService implements GetEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public GetEmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public GetEmployeeResponse getEmployee(Long employeeId) {
        if(!employeeRepository.existsById(employeeId)){
            throw new EmployeeNotFoundException();
        }
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return employeeMapper.mapEmployeeToEmployeeResponse(employee);
    }
}
