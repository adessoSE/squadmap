package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeService implements GetEmployeeUseCase {

    private final GetEmployeePort getEmployeePort;
    private final Mapper<Employee, GetEmployeeResponse> employeeMapper;

    public GetEmployeeService(GetEmployeePort getEmployeePort, Mapper<Employee, GetEmployeeResponse> employeeMapper) {
        this.getEmployeePort = getEmployeePort;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public GetEmployeeResponse getEmployee(Long employeeId) {
        return employeeMapper.map(getEmployeePort.getEmployee(employeeId));
    }
}
