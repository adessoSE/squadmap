package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.adapter.persistence.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class DeleteEmployeeAdapterTest {


    @MockBean
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeleteEmployeeAdapter deleteEmployeePort;

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() {
        //given
        long employeeId = 1;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(employeeId);

        //when
        deleteEmployeePort.deleteEmployee(employeeId);

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfDeleteEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //when
        assertThrows(EmployeeNotFoundException.class, () -> deleteEmployeePort.deleteEmployee(employeeId));

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }
}
