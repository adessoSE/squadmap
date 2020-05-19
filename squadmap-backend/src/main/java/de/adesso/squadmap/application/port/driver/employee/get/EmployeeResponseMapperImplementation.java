package de.adesso.squadmap.application.port.driver.employee.get;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.mapper.EmployeeResponseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class EmployeeResponseMapperImplementation implements EmployeeResponseMapper {

    @Override
    public GetEmployeeResponse mapToResponseEntity(Employee employee, List<WorkingOn> workingOns) {
        return GetEmployeeResponse.of(employee, workingOns);
    }
}
