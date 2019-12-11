package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import org.springframework.stereotype.Component;

@Component
public class CreateCommandToEmployeeMapper implements Mapper<CreateEmployeeCommand, Employee> {

    @Override
    public Employee map(CreateEmployeeCommand command) {
        return new Employee(
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.getIsExternal());
    }
}
