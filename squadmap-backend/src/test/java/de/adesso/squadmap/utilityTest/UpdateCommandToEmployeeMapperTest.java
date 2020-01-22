package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.utility.UpdateCommandToEmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateCommandToEmployeeMapperTest {

    @Autowired
    private UpdateCommandToEmployeeMapper employeeMapper;

    @Test
    void checkIfMapMapsUpdateCommandToValidEmployee() {
        //given
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now(), "e", "p", true, "i");

        //when
        Employee employee  = employeeMapper.map(updateEmployeeCommand);

        //then
        assertThat(employee.getFirstName()).isEqualTo(updateEmployeeCommand.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(updateEmployeeCommand.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(updateEmployeeCommand.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(updateEmployeeCommand.getEmail());
        assertThat(employee.getPhone()).isEqualTo(updateEmployeeCommand.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(updateEmployeeCommand.isExternal());
        assertThat(employee.getImage()).isEqualTo(updateEmployeeCommand.getImage());
    }
}
