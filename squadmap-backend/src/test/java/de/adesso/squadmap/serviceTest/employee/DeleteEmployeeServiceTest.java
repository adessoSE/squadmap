package de.adesso.squadmap.serviceTest.employee;

import de.adesso.squadmap.exceptions.employee.EmployeeNotFoundException;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.service.employee.DeleteEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DeleteEmployeeServiceTest {

    @Autowired
    private DeleteEmployeeService service;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() {
        //given
        long employeeId = 1;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(employeeId);

        //when
        service.deleteEmployee(employeeId);

        //then
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfDeleteEmployeeThrowsExceptionWhenNotFound() throws EmployeeNotFoundException {
        //given
        long employeeId = 1;
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () ->
                service.deleteEmployee(employeeId));
    }
}
