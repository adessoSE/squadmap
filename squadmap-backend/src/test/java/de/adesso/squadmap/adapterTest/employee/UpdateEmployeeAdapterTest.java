package de.adesso.squadmap.adapterTest.employee;

import de.adesso.squadmap.adapter.employee.UpdateEmployeeAdapter;
import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateEmployeeAdapterTest {

    @Autowired
    private UpdateEmployeeAdapter updateEmployeeAdapter;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        Employee employee = new Employee();
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeAlreadyExistsException() {

    }
}
