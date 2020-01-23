package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.workingon.ListWorkingOnPort;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class ListEmployeeService implements ListEmployeeUseCase {

    private final ListEmployeePort listEmployeePort;
    private final ListWorkingOnPort listWorkingOnPort;

    @Override
    public List<GetEmployeeResponse> listEmployees() {
        List<GetEmployeeResponse> responses = new ArrayList<>();
        List<WorkingOn> allRelations = listWorkingOnPort.listWorkingOn();
        listEmployeePort.listEmployees().forEach(employee ->
                responses.add(GetEmployeeResponse.getInstance(employee, allRelations)));
        return responses;
    }
}
