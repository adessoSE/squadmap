package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.EmployeeMother;
import de.adesso.squadmap.domain.exceptions.EmployeeAlreadyExistsException;
import de.adesso.squadmap.domain.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UpdateEmployeeAdapterTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PersistenceMapper<Employee, EmployeeNeo4JEntity> employeePersistenceMapper;
    @InjectMocks
    private UpdateEmployeeAdapter updateEmployeeAdapter;

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .email("e")
                .build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete()
                .employeeId(employeeId)
                .email("e")
                .build();
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        when(employeePersistenceMapper.mapToNeo4JEntity(employee)).thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity, 0)).thenReturn(employeeNeo4JEntity);

        //when
        updateEmployeeAdapter.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId(), 0);
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeePersistenceMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity, 0);
        verifyNoMoreInteractions(employeePersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployeeWithEmailChanged() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .email("e")
                .build();
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete()
                .employeeId(employeeId)
                .email("f")
                .build();
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.of(employeeNeo4JEntity));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeePersistenceMapper.mapToNeo4JEntity(employee))
                .thenReturn(employeeNeo4JEntity);
        when(employeeRepository.save(employeeNeo4JEntity, 0)).thenReturn(employeeNeo4JEntity);

        //when
        updateEmployeeAdapter.updateEmployee(employee);

        //then
        verify(employeeRepository, times(1)).findById(employee.getEmployeeId(), 0);
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verify(employeePersistenceMapper, times(1)).mapToNeo4JEntity(employee);
        verify(employeeRepository, times(1)).save(employeeNeo4JEntity, 0);
        verifyNoMoreInteractions(employeePersistenceMapper);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeNotFoundException() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .build();
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.empty());

        //when
        assertThrows(EmployeeNotFoundException.class, () -> updateEmployeeAdapter.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId, 0);
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(employeePersistenceMapper);
    }

    @Test
    void checkIfUpdateEmployeeThrowsEmployeeAlreadyExistsException() {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete()
                .employeeId(employeeId)
                .email("e")
                .build();
        EmployeeNeo4JEntity existingEmployee = EmployeeNeo4JEntityMother.complete()
                .employeeId(employeeId)
                .email("f")
                .build();
        when(employeeRepository.findById(employeeId, 0)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        //when
        assertThrows(EmployeeAlreadyExistsException.class, () -> updateEmployeeAdapter.updateEmployee(employee));

        //then
        verify(employeeRepository, times(1)).findById(employeeId, 0);
        verify(employeeRepository, times(1)).existsByEmail(employee.getEmail());
        verifyNoMoreInteractions(employeeRepository);
        verifyNoInteractions(employeePersistenceMapper);
    }
}
