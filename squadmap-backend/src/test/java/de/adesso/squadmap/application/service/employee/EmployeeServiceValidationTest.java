package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.domain.mapper.EmployeeDomainMapper;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driven.employee.UpdateEmployeePort;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommandMother;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceValidationTest {

    @MockBean
    private CreateEmployeePort createEmployeePort;
    @MockBean
    private UpdateEmployeePort updateEmployeePort;
    @MockBean
    private EmployeeDomainMapper employeeDomainMapper;
    @Autowired
    private CreateEmployeeService createEmployeeService;
    @Autowired
    private UpdateEmployeeService updateEmployeeService;

    //@Test
    void checkIfCreateEmployeeRejectsInvalidEmployee() {
        //given
        CreateEmployeeCommand firstNameNull = CreateEmployeeCommandMother.complete().firstName(null).build();
        CreateEmployeeCommand invalidFirstNameLength = CreateEmployeeCommandMother.complete().firstName("").build();

        CreateEmployeeCommand lastNameNull = CreateEmployeeCommandMother.complete().lastName(null).build();
        CreateEmployeeCommand invalidLastNameLength = CreateEmployeeCommandMother.complete().lastName("").build();

        CreateEmployeeCommand birthdayNull = CreateEmployeeCommandMother.complete().birthday(null).build();
        CreateEmployeeCommand birthdayInFuture = CreateEmployeeCommandMother.complete()
                .birthday(LocalDate.now().plusDays(1)).build();

        CreateEmployeeCommand emailNull = CreateEmployeeCommandMother.complete().email(null).build();
        CreateEmployeeCommand invalidEmail = CreateEmployeeCommandMother.complete().email("").build();

        CreateEmployeeCommand phoneNull = CreateEmployeeCommandMother.complete().phone(null).build();
        CreateEmployeeCommand invalidPhone = CreateEmployeeCommandMother.complete()
                .phone("111111111111111111111").build();

        CreateEmployeeCommand isExternalNull = CreateEmployeeCommandMother.complete().isExternal(null).build();

        CreateEmployeeCommand imageNull = CreateEmployeeCommandMother.complete().image(null).build();

        //when
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(firstNameNull));
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(invalidFirstNameLength));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(lastNameNull));
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(invalidLastNameLength));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(birthdayNull));
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(birthdayInFuture));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(emailNull));
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(invalidEmail));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(phoneNull));
        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(invalidPhone));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(isExternalNull));

        assertThrows(ConstraintViolationException.class, () ->
                createEmployeeService.createEmployee(imageNull));
    }

    //@Test
    void checkIfUpdateEmployeeRejectsInvalidEmployee() {
        //given
        long employeeId = 0L;
        UpdateEmployeeCommand firstNameNull = UpdateEmployeeCommandMother.complete().firstName(null).build();
        UpdateEmployeeCommand invalidFirstNameLength = UpdateEmployeeCommandMother.complete().firstName("").build();

        UpdateEmployeeCommand lastNameNull = UpdateEmployeeCommandMother.complete().lastName(null).build();
        UpdateEmployeeCommand invalidLastNameLength = UpdateEmployeeCommandMother.complete().lastName("").build();

        UpdateEmployeeCommand birthdayNull = UpdateEmployeeCommandMother.complete().birthday(null).build();
        UpdateEmployeeCommand birthdayInFuture = UpdateEmployeeCommandMother.complete()
                .birthday(LocalDate.now().plusDays(1)).build();

        UpdateEmployeeCommand emailNull = UpdateEmployeeCommandMother.complete().email(null).build();
        UpdateEmployeeCommand invalidEmail = UpdateEmployeeCommandMother.complete().email("").build();

        UpdateEmployeeCommand phoneNull = UpdateEmployeeCommandMother.complete().phone(null).build();
        UpdateEmployeeCommand invalidPhone = UpdateEmployeeCommandMother.complete()
                .phone("111111111111111111111").build();

        UpdateEmployeeCommand isExternalNull = UpdateEmployeeCommandMother.complete().isExternal(null).build();

        UpdateEmployeeCommand imageNull = UpdateEmployeeCommandMother.complete().image(null).build();

        //when
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(firstNameNull, employeeId));
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(invalidFirstNameLength, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(lastNameNull, employeeId));
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(invalidLastNameLength, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(birthdayNull, employeeId));
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(birthdayInFuture, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(emailNull, employeeId));
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(invalidEmail, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(phoneNull, employeeId));
        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(invalidPhone, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(isExternalNull, employeeId));

        assertThrows(ConstraintViolationException.class, () ->
                updateEmployeeService.updateEmployee(imageNull, employeeId));
    }
}
