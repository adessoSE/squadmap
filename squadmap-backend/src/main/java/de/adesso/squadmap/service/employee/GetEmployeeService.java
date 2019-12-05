package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.utility.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeService implements GetEmployeeUseCase {

    private final EmployeeRepository employeeRepository;

    public GetEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public GetEmployeeResponse getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return EmployeeMapper.mapEmployeeToEmployeeResponse(employee);
    }
}
