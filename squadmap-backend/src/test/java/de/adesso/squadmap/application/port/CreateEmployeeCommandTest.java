package de.adesso.squadmap.application.port;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ActiveProfiles("test")
class CreateEmployeeCommandTest {

    @Autowired
    private LocalValidatorFactoryBean validator;
    
    //@Test
    void checkIfCreateEmployeeCommandEmailsAreValid() {
        CreateEmployeeCommand createEmployeeCommand1 = CreateEmployeeCommandMother.complete()
                .email("a.b@c.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand2 = CreateEmployeeCommandMother.complete()
                .email( "a@c.s")
                .build();
        CreateEmployeeCommand createEmployeeCommand3 = CreateEmployeeCommandMother.complete()
                .email( "#?!@s.lo")
                .build();
        CreateEmployeeCommand createEmployeeCommand4 = CreateEmployeeCommandMother.complete()
                .email( "a.b.c@d.de")
                .build();

        assertThat(validator.validateProperty(createEmployeeCommand1, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "email").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "email").size()).isEqualTo(0);
    }

    //@Test
    void checkIfCreateEmployeeCommandEmailsAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand1 = CreateEmployeeCommandMother.complete()
                .email( "a.@c.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand2 = CreateEmployeeCommandMother.complete()
                .email( "@s.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand3 = CreateEmployeeCommandMother.complete()
                .email( "a@b@c.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand4 = CreateEmployeeCommandMother.complete()
                .email( "a b@c.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand5 = CreateEmployeeCommandMother.complete()
                .email( "a\"@b.de")
                .build();
        CreateEmployeeCommand createEmployeeCommand6 = CreateEmployeeCommandMother.complete()
                .email( "a@b.")
                .build();
        CreateEmployeeCommand createEmployeeCommand7 = CreateEmployeeCommandMother.complete()
                .email( "a@.de")
                .build();

        assertThat(validator.validateProperty(createEmployeeCommand1, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "email").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "email").size()).isGreaterThan(0);
    }

    //@Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreValid() {
        CreateEmployeeCommand createEmployeeCommand1 = CreateEmployeeCommandMother.complete()
                .phone("0123456789")
                .build();
        CreateEmployeeCommand createEmployeeCommand2 = CreateEmployeeCommandMother.complete()
                .phone("(0123)456789")
                .build();
        CreateEmployeeCommand createEmployeeCommand3 = CreateEmployeeCommandMother.complete()
                .phone("(0123) 456789")
                .build();
        CreateEmployeeCommand createEmployeeCommand4 = CreateEmployeeCommandMother.complete()
                .phone("(01234)56789")
                .build();
        CreateEmployeeCommand createEmployeeCommand5 = CreateEmployeeCommandMother.complete()
                .phone("01234-56789")
                .build();
        CreateEmployeeCommand createEmployeeCommand6 = CreateEmployeeCommandMother.complete()
                .phone("0-1-2-3-4-5-6-7-8-9")
                .build();
        CreateEmployeeCommand createEmployeeCommand7 = CreateEmployeeCommandMother.complete()
                .phone("0 1 2 3 4 5 6 7 8 9")
                .build();

        assertThat(validator.validateProperty(createEmployeeCommand1, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "phone").size()).isEqualTo(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "phone").size()).isEqualTo(0);
    }

    //@Test
    void checkIfCreateEmployeeCommandPhoneNumbersAreNotValid() {
        CreateEmployeeCommand createEmployeeCommand1 = CreateEmployeeCommandMother.complete()
                .phone("0")
                .build();
        CreateEmployeeCommand createEmployeeCommand2 = CreateEmployeeCommandMother.complete()
                .phone("00")
                .build();
        CreateEmployeeCommand createEmployeeCommand3 = CreateEmployeeCommandMother.complete()
                .phone("000")
                .build();
        CreateEmployeeCommand createEmployeeCommand4 = CreateEmployeeCommandMother.complete()
                .phone("0000")
                .build();
        CreateEmployeeCommand createEmployeeCommand5 = CreateEmployeeCommandMother.complete()
                .phone("00000")
                .build();
        CreateEmployeeCommand createEmployeeCommand6 = CreateEmployeeCommandMother.complete()
                .phone("000000")
                .build();
        CreateEmployeeCommand createEmployeeCommand7 = CreateEmployeeCommandMother.complete()
                .phone("1234567§9")
                .build();
        
        assertThat(validator.validateProperty(createEmployeeCommand1, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand2, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand3, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand4, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand5, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand6, "phone").size()).isGreaterThan(0);
        assertThat(validator.validateProperty(createEmployeeCommand7, "phone").size()).isGreaterThan(0);
    }
}
