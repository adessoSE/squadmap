package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
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
        return new GetEmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal());
    }
}
