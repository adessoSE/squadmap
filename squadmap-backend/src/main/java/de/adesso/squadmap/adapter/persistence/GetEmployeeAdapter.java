package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.GetEmployeePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GetEmployeeAdapter implements GetEmployeePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeePersistenceMapper mapper;

    @Override
    public Employee getEmployee(Long employeeId) {
        return mapper.mapToDomainEntity(employeeRepository.findById(employeeId, 0)
                .orElseThrow(EmployeeNotFoundException::new));
    }
}
