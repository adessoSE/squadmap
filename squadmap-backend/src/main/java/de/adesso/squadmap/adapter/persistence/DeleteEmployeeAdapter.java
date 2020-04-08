package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import de.adesso.squadmap.application.port.driven.employee.DeleteEmployeePort;
import de.adesso.squadmap.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class DeleteEmployeeAdapter implements DeleteEmployeePort {

    private final EmployeeRepository employeeRepository;

    @Override
    public void deleteEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException(employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }
}
