package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ListEmployeeService implements ListEmployeeUseCase {

    private final ListEmployeePort listEmployeePort;

    @Override
    @Transactional
    public List<Employee> listEmployees() {
        return listEmployeePort.listEmployees();
    }
}
