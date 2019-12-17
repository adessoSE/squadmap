package de.adesso.squadmap.service.employee;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateEmployeeService implements UpdateEmployeeUseCase {

    private final EmployeeRepository employeeRepository;

    public UpdateEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void updateEmployee(UpdateEmployeeCommand command, Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException();
        }
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        employee.setFirstName(command.getFirstName());
        employee.setLastName(command.getLastName());
        employee.setBirthday(command.getBirthday());
        employee.setEmail(command.getEmail());
        employee.setPhone(command.getPhone());
        employee.setIsExternal(command.isExternal());
        employeeRepository.save(employee);
    }
}
