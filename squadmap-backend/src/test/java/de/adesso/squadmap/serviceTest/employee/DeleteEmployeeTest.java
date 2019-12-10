package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.DeleteEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DeleteEmployeeTest {

    @Autowired
    private DeleteEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {

    }
}
