package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.DeleteEmployeePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DeleteEmployeeServiceTest {

    @Autowired
    private DeleteEmployeeService service;
    @MockBean
    private DeleteEmployeePort deleteEmployeePort;

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() {
        //given
        long employeeId = 1;
        doNothing().when(deleteEmployeePort).deleteEmployee(employeeId);

        //when
        service.deleteEmployee(employeeId);

        //then
        verify(deleteEmployeePort, times(1)).deleteEmployee(employeeId);
        verifyNoMoreInteractions(deleteEmployeePort);
    }
}
