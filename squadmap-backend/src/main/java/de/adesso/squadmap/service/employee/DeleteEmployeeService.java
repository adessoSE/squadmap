package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteEmployeeService implements DeleteEmployeeUseCase {

    private final EmployeeRepository employeeRepository;

    DeleteEmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
