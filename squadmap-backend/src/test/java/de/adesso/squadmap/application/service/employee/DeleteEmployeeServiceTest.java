package de.adesso.squadmap.application.service.employee;

import de.adesso.squadmap.application.port.driven.employee.DeleteEmployeePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DeleteEmployeeServiceTest {

    @Mock
    private DeleteEmployeePort deleteEmployeePort;
    @InjectMocks
    private DeleteEmployeeService deleteEmployeeService;

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() {
        //given
        long employeeId = 1;
        doNothing().when(deleteEmployeePort).deleteEmployee(employeeId);

        //when
        deleteEmployeeService.deleteEmployee(employeeId);

        //then
        verify(deleteEmployeePort, times(1)).deleteEmployee(employeeId);
        verifyNoMoreInteractions(deleteEmployeePort);
    }
}
