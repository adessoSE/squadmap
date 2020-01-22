package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommandToEmployeeMapper implements Mapper<UpdateEmployeeCommand, Employee> {

    @Override
    public Employee map(UpdateEmployeeCommand command) {
        return new Employee(
                command.getFirstName(),
                command.getLastName(),
                command.getBirthday(),
                command.getEmail(),
                command.getPhone(),
                command.isExternal(),
                command.getImage());
    }
}
