package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetEmployeeService implements GetEmployeeUseCase {

    private final GetEmployeePort getEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;

    @Override
    public GetEmployeeResponse getEmployee(Long employeeId) {
        return GetEmployeeResponse.getInstance(
                getEmployeePort.getEmployee(employeeId),
                listWorkingOnPort.listWorkingOn());
    }
}
