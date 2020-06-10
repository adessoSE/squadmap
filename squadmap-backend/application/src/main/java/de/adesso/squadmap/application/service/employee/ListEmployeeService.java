package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.domain.mapper.EmployeeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ListEmployeeService implements ListEmployeeUseCase {

    private final ListEmployeePort listEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final EmployeeResponseMapper employeeResponseMapper;

    @Override
    @Transactional
    public List<GetEmployeeResponse> listEmployees() {
        List<WorkingOn> workingOns = listWorkingOnPort.listWorkingOn();
        return listEmployeePort.listEmployees().stream()
                .map(employee -> employeeResponseMapper.mapToResponseEntity(
                        employee,
                        workingOns.stream()
                                .filter(workingOn -> workingOn.getEmployee().getEmployeeId()
                                        .equals(employee.getEmployeeId()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
