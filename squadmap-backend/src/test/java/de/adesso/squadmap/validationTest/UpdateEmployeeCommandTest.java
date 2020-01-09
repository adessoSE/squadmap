package de.adesso.squadmap.validationTest;


import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UpdateEmployeeCommandTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    void checkIfUpdateEmployeeCommandEmailsAreValid() {
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();

        updateEmployeeCommand.setEmail("a.b@c.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isEqualTo(0);
        updateEmployeeCommand.setEmail("a@c.s");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isEqualTo(0);
        updateEmployeeCommand.setEmail("#?!@s.lo");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isEqualTo(0);
        updateEmployeeCommand.setEmail("a.b.c@d.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isEqualTo(0);
    }

    @Test
    void checkIfUpdateEmployeeCommandEmailsAreNotValid() {
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();

        updateEmployeeCommand.setEmail("a.@c.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("@s.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("a@b@c.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("a b@c.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("a\"@b.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("a@b.");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
        updateEmployeeCommand.setEmail("a@.de");
        assertThat(validator.validateProperty(updateEmployeeCommand, "email").size()).isGreaterThan(0);
    }

    @Test
    void checkIfUpdateEmployeeCommandPhoneNumbersAreValid() {
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();

        updateEmployeeCommand.setPhone("0123456789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
        updateEmployeeCommand.setPhone("(0123)456789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
        updateEmployeeCommand.setPhone("(0123) 456789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
        updateEmployeeCommand.setPhone("(01234)56789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
        updateEmployeeCommand.setPhone("01234-56789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
        updateEmployeeCommand.setPhone("01234 56789");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isEqualTo(0);
    }

    @Test
    void checkIfUpdateEmployeeCommandPhoneNumbersAreNotValid() {
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();

        updateEmployeeCommand.setPhone("0");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
        updateEmployeeCommand.setPhone("00");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
        updateEmployeeCommand.setPhone("000");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
        updateEmployeeCommand.setPhone("0000");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
        updateEmployeeCommand.setPhone("00000");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
        updateEmployeeCommand.setPhone("000000");
        assertThat(validator.validateProperty(updateEmployeeCommand, "phone").size()).isGreaterThan(0);
    }
}
