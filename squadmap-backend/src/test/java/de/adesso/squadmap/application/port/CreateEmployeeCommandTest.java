package de.adesso.squadmap.application.port;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
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
        CreateEmployeeCommand createEmployeeCommand1 = new CreateEmployeeCommand(
                "", "", null, "a.b@c.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand2 = new CreateEmployeeCommand(
                "", "", null, "a@c.s", "", true, "");
        CreateEmployeeCommand createEmployeeCommand3 = new CreateEmployeeCommand(
                "", "", null, "#?!@s.lo", "", true, "");
        CreateEmployeeCommand createEmployeeCommand4 = new CreateEmployeeCommand(
                "", "", null, "a.b.c@d.de", "", true, "");

        assertThat(validator.validateProperty(createEmployeeCommand1, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "email").size()).isEqualTo(0);
    }

    @Test
    void checkIfCreateEmployeeCommandEmailsAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand1 = new CreateEmployeeCommand(
                "", "", null, "a.@c.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand2 = new CreateEmployeeCommand(
                "", "", null, "@s.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand3 = new CreateEmployeeCommand(
                "", "", null, "a@b@c.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand4 = new CreateEmployeeCommand(
                "", "", null, "a b@c.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand5 = new CreateEmployeeCommand(
                "", "", null, "a\"@b.de", "", true, "");
        CreateEmployeeCommand createEmployeeCommand6 = new CreateEmployeeCommand(
                "", "", null, "a@b.", "", true, "");
        CreateEmployeeCommand createEmployeeCommand7 = new CreateEmployeeCommand(
                "", "", null, "a@.de", "", true, "");

        assertThat(validator.validateProperty(createEmployeeCommand1, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "email").size()).isGreaterThan(0);
    }

    @Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreValid() {
        CreateEmployeeCommand createEmployeeCommand1 = new CreateEmployeeCommand(
                "", "", null, "", "0123456789", true, "");
        CreateEmployeeCommand createEmployeeCommand2 = new CreateEmployeeCommand(
                "", "", null, "", "(0123)456789", true, "");
        CreateEmployeeCommand createEmployeeCommand3 = new CreateEmployeeCommand(
                "", "", null, "", "(0123) 456789", true, "");
        CreateEmployeeCommand createEmployeeCommand4 = new CreateEmployeeCommand(
                "", "", null, "", "(01234)56789", true, "");
        CreateEmployeeCommand createEmployeeCommand5 = new CreateEmployeeCommand(
                "", "", null, "", "01234-56789", true, "");
        CreateEmployeeCommand createEmployeeCommand6 = new CreateEmployeeCommand(
                "", "", null, "", "0-1-2-3-4-5-6-7-8-9", true, "");
        CreateEmployeeCommand createEmployeeCommand7 = new CreateEmployeeCommand(
                "", "", null, "", "0 1 2 3 4 5 6 7 8 9", true, "");

        assertThat(validator.validateProperty(createEmployeeCommand1, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "phone").size()).isEqualTo(0);
    }

    @Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand1 = new CreateEmployeeCommand(
                "", "", null, "", "0", true, "");
        CreateEmployeeCommand createEmployeeCommand2 = new CreateEmployeeCommand(
                "", "", null, "", "00", true, "");
        CreateEmployeeCommand createEmployeeCommand3 = new CreateEmployeeCommand(
                "", "", null, "", "", true, "");
        CreateEmployeeCommand createEmployeeCommand4 = new CreateEmployeeCommand(
                "", "", null, "", "000", true, "");
        CreateEmployeeCommand createEmployeeCommand5 = new CreateEmployeeCommand(
                "", "", null, "", "0000", true, "");
        CreateEmployeeCommand createEmployeeCommand6 = new CreateEmployeeCommand(
                "", "", null, "", "000000", true, "");
        CreateEmployeeCommand createEmployeeCommand7 = new CreateEmployeeCommand(
                "", "", null, "", "1234567§9", true, "");

        assertThat(validator.validateProperty(createEmployeeCommand1, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "phone").size()).isGreaterThan(0);
    }
}
