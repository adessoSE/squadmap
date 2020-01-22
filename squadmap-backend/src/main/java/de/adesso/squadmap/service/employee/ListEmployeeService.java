package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.utility.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListEmployeeService implements ListEmployeeUseCase {

    private final ListEmployeePort listEmployeePort;
    private final Mapper<Employee, GetEmployeeResponse> employeeMapper;

    public ListEmployeeService(ListEmployeePort listEmployeePort, Mapper<Employee, GetEmployeeResponse> employeeMapper){
        this.listEmployeePort = listEmployeePort;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<GetEmployeeResponse> listEmployees() {
        List<GetEmployeeResponse> responses = new ArrayList<>();
        listEmployeePort.listEmployees().forEach(employee -> responses.add(employeeMapper.map(employee)));
        return responses;
    }
}
