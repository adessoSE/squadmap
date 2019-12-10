package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.EmployeeController;
import de.adesso.squadmap.service.employee.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeControllerTest {

    @Mock
    private CreateEmployeeService createEmployeeService;
    @Mock
    private DeleteEmployeeService deleteEmployeeService;
    @Mock
    private GetEmployeeService getEmployeeService;
    @Mock
    private ListEmployeeService listEmployeeService;
    @Mock
    private UpdateEmployeeService updateEmployeeService;
    @InjectMocks
    private EmployeeController employeeController;
}
