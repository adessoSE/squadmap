package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.domain.mapper.EmployeeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class GetEmployeeService implements GetEmployeeUseCase {

    private final GetEmployeePort getEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;
    private final EmployeeResponseMapper employeeResponseMapper;

    @Override
    @Transactional
    public GetEmployeeResponse getEmployee(Long employeeId) {
        return employeeResponseMapper.mapToResponseEntity(
                getEmployeePort.getEmployee(employeeId),
                listWorkingOnPort.listWorkingOnByEmployeeId(employeeId));
    }
}
