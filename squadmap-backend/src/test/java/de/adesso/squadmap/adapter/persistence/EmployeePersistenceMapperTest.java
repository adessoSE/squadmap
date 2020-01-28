package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeePersistenceMapperTest {

    @Autowired
    private EmployeePersistenceMapper employeePersistenceMapper;

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntity() {
        //given
        Employee employee = EmployeeMother.complete().build();

        //when
        EmployeeNeo4JEntity employeeNeo4JEntity = employeePersistenceMapper.mapToNeo4JEntity(employee);

        //then
        assertThat(employeeNeo4JEntity.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeNeo4JEntity.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeNeo4JEntity.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeNeo4JEntity.getBirthday()).isEqualTo(employee.getBirthday());
        assertThat(employeeNeo4JEntity.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeNeo4JEntity.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeNeo4JEntity.getIsExternal()).isEqualTo(employee.getIsExternal());
        assertThat(employeeNeo4JEntity.getImage()).isEqualTo(employee.getImage());
        assertThat(employeeNeo4JEntity.getProjects()).isEmpty();
    }

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntityWithList() {
        //given
        Employee employee = EmployeeMother.complete().build();
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete()
                .employee(EmployeeNeo4JEntityMother.complete()
                        .employeeId(employee.getEmployeeId())
                        .build())
                .build();
        List<WorkingOnNeo4JEntity> relations = Collections.singletonList(workingOnNeo4JEntity);

        //when
        EmployeeNeo4JEntity employeeNeo4JEntity = employeePersistenceMapper.mapToNeo4JEntity(employee, relations);

        //then
        assertThat(employeeNeo4JEntity.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeNeo4JEntity.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeNeo4JEntity.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeNeo4JEntity.getBirthday()).isEqualTo(employee.getBirthday());
        assertThat(employeeNeo4JEntity.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeNeo4JEntity.getPhone()).isEqualTo(employee.getPhone());
        assertThat(employeeNeo4JEntity.getIsExternal()).isEqualTo(employee.getIsExternal());
        assertThat(employeeNeo4JEntity.getImage()).isEqualTo(employee.getImage());
        assertThat(employeeNeo4JEntity.getProjects()).isEqualTo(relations);
    }

    @Test
    void checkIfMapToDomainEntityMapsToValidEntityFromNeo4JEntity() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();

        //when
        Employee employee = employeePersistenceMapper.mapToDomainEntity(employeeNeo4JEntity);

        //then
        assertThat(employee.getEmployeeId()).isEqualTo(employeeNeo4JEntity.getEmployeeId());
        assertThat(employee.getFirstName()).isEqualTo(employeeNeo4JEntity.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(employeeNeo4JEntity.getLastName());
        assertThat(employee.getBirthday()).isEqualTo(employeeNeo4JEntity.getBirthday());
        assertThat(employee.getEmail()).isEqualTo(employeeNeo4JEntity.getEmail());
        assertThat(employee.getPhone()).isEqualTo(employeeNeo4JEntity.getPhone());
        assertThat(employee.getIsExternal()).isEqualTo(employeeNeo4JEntity.getIsExternal());
        assertThat(employee.getImage()).isEqualTo(employeeNeo4JEntity.getImage());
    }
}
