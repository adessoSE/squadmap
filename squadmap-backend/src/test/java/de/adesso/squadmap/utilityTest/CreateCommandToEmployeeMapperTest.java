package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.utility.CreateCommandToEmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CreateCommandToEmployeeMapperTest {

    @Autowired
    private CreateCommandToEmployeeMapper mapper;

    @Test
    void checkIfMapMapsToValidEmployee() {
        //given
        CreateEmployeeCommand command = new CreateEmployeeCommand("f", "l", LocalDate.now(), "e", "0", true);

        //when
        Employee employee = mapper.map(command);

        //then
        assertThat(employee.getFirstName()).isEqualTo(command.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(command.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(command.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(command.getEmail());
        assertThat(employee.getPhone()).isEqualTo(command.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(command.getIsExternal());
        assertThat(employee.getProjects()).isEmpty();
    }

}
