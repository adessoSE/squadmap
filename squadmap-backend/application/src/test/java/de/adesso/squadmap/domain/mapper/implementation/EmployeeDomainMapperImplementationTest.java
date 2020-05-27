package de.adesso.squadmap.domain.mapper.implementation;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommandMother;
import de.adesso.squadmap.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class EmployeeDomainMapperImplementationTest {

    private EmployeeDomainMapperImplementation employeeMapper = new EmployeeDomainMapperImplementation();

    @Test
    void checkIfMapToDomainEntityMapsCreateCommand() {
        //given
        CreateEmployeeCommand createEmployeeCommand = CreateEmployeeCommandMother.complete().build();

        //when
        Employee employee = employeeMapper.mapToDomainEntity(createEmployeeCommand);

        //then
        assertThat(employee.getEmployeeId()).isNull();
        assertThat(employee.getFirstName()).isEqualTo(createEmployeeCommand.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(createEmployeeCommand.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(createEmployeeCommand.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(createEmployeeCommand.getEmail());
        assertThat(employee.getPhone()).isEqualTo(createEmployeeCommand.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(createEmployeeCommand.getIsExternal());
        assertThat(employee.getImage()).isEqualTo(createEmployeeCommand.getImage());
    }

    @Test
    void checkIfMapToDomainEntityMapsUpdateCommand() {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand updateEmployeeCommand = UpdateEmployeeCommandMother.complete().build();

        //when
        Employee employee = employeeMapper.mapToDomainEntity(updateEmployeeCommand, employeeId);

        //then
        assertThat(employee.getEmployeeId()).isEqualTo(employeeId);
        assertThat(employee.getFirstName()).isEqualTo(updateEmployeeCommand.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(updateEmployeeCommand.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(updateEmployeeCommand.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(updateEmployeeCommand.getEmail());
        assertThat(employee.getPhone()).isEqualTo(updateEmployeeCommand.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(updateEmployeeCommand.getIsExternal());
        assertThat(employee.getImage()).isEqualTo(updateEmployeeCommand.getImage());
    }
}
