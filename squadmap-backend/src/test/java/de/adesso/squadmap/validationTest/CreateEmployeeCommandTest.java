package de.adesso.squadmap.validationTest;

import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CreateEmployeeCommandTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    void checkIfCreateEmployeeCommandEmailsAreValid() {
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

        createEmployeeCommand.setEmail("a.b@c.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isEqualTo(0);
        createEmployeeCommand.setEmail("a@c.s");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isEqualTo(0);
        createEmployeeCommand.setEmail("#?!@s.lo");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isEqualTo(0);
        createEmployeeCommand.setEmail("a.b.c@d.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isEqualTo(0);
    }

    @Test
    void checkIfCreateEmployeeCommandEmailsAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

        createEmployeeCommand.setEmail("a.@c.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("@s.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("a@b@c.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("a b@c.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("a\"@b.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("a@b.");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
        createEmployeeCommand.setEmail("a@.de");
        assertThat(validator.validateProperty(createEmployeeCommand, "email").size()).isGreaterThan(0);
    }

    @Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreValid() {
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

        createEmployeeCommand.setPhone("0123456789");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("(0123)456789");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("(0123) 456789");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("(01234)56789");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("01234-56789");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("0-1-2-3-4-5-6-7-8-9");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
        createEmployeeCommand.setPhone("0 1 2 3 4 5 6 7 8 9");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isEqualTo(0);
    }

    @Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

        createEmployeeCommand.setPhone("0");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("00");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("000");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("0000");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("00000");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("000000");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
        createEmployeeCommand.setPhone("1234567§9");
        assertThat(validator.validateProperty(createEmployeeCommand, "phone").size()).isGreaterThan(0);
    }
}
