package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.ResponseMapper;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetEmployeeService implements GetEmployeeUseCase {

    private final GetEmployeePort getEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;

    @Override
    @Transactional
    public GetEmployeeResponse getEmployee(Long employeeId) {
        return employeeResponseMapper.toResponse(
                getEmployeePort.getEmployee(employeeId),
                listWorkingOnPort.listWorkingOn());
    }
}
