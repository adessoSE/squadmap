package de.adesso.squadmap.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WorkingOnMapperTest {

    @Autowired
    private WorkingOnMapper workingOnMapper;

    @Test
    void checkIfMapToNeo4JEntityMapsToValidEntity() {
    }

    @Test
    void checkIfMapToDomainEntityMapsToValidEntityFromNeo4JEntity() {

    }

    @Test
    void checkIfMapToResponseMapsToValidResponse() {
        //        //given
//
//        Project project = new Project("t", "d", LocalDate.now(), LocalDate.now(), true, new ArrayList<>());
//        Employee employee = new Employee("f", "l", LocalDate.now(), "e", "0", true, "");
//        WorkingOn workingOn = new WorkingOn(employee, project, LocalDate.now(), LocalDate.now(), 0);
//        workingOn.setWorkingOnId(1L);
//
//        GetEmployeeResponse employeeResponse = new GetEmployeeResponse();
//        GetProjectResponse projectResponse = new GetProjectResponse();
//        when(employeeMapper.map(employee)).thenReturn(employeeResponse);
//        when(projectMapper.map(project)).thenReturn(projectResponse);
//
//        //when
//        GetWorkingOnResponse workingOnResponse = mapper.map(workingOn);
//
//        //then
//        assertThat(workingOnResponse.getWorkingOnId()).isEqualTo(workingOn.getWorkingOnId());
//        assertThat(workingOnResponse.getSince()).isEqualTo(workingOn.getSince());
//        assertThat(workingOnResponse.getUntil()).isEqualTo(workingOn.getUntil());
//        assertThat(workingOnResponse.getWorkload()).isEqualTo(workingOn.getWorkload());
//
//        assertThat(workingOnResponse.getEmployee()).isEqualTo(employeeResponse);
//        assertThat(workingOnResponse.getProject()).isEqualTo(projectResponse);
//
//        verify(projectMapper, times(1)).map(project);
//        verify(employeeMapper, times(1)).map(employee);
    }
}
