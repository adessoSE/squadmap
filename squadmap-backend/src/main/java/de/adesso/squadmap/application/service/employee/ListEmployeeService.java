package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ListEmployeeService implements ListEmployeeUseCase {

    private final ListEmployeePort listEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;

    @Override
    @Transactional
    public List<GetEmployeeResponse> listEmployees() {
        List<WorkingOn> allRelations = listWorkingOnPort.listWorkingOn();
        return listEmployeePort.listEmployees().stream()
                .map(employee -> employeeResponseMapper.toResponse(employee, allRelations))
                .collect(Collectors.toList());
    }
}
