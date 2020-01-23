package de.adesso.squadmap.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@ActiveProfiles("test")
class WorkingOnRepositoryTest {

    @Autowired
    private WorkingOnRepository workingOnRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void checkIfExistsByEmployeeAndProjectReturnsTrueWhenTrue() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity = new EmployeeNeo4JEntity(
                "", "", null, "", "", true, "");
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                "", "", null, null, true, new ArrayList<>());
        WorkingOnNeo4JEntity workingOnNeo4JEntity = new WorkingOnNeo4JEntity(
                null, null, 0, employeeNeo4JEntity, projectNeo4JEntity);
        projectRepository.save(projectNeo4JEntity);
        employeeRepository.save(employeeNeo4JEntity);
        workingOnRepository.save(workingOnNeo4JEntity);

        //when
        boolean answer = workingOnRepository.existsByEmployeeAndProject(employeeNeo4JEntity.getEmployeeId(), projectNeo4JEntity.getProjectId());

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void checkIfExistsByEmployeeAndProjectReturnsFalseWhenFalse() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity = new EmployeeNeo4JEntity(
                1L, "", "", null, "", "", true, "");
        ProjectNeo4JEntity projectNeo4JEntity = new ProjectNeo4JEntity(
                1L, "", "", null, null, true, new ArrayList<>());

        //when
        boolean answer = workingOnRepository.existsByEmployeeAndProject(
                employeeNeo4JEntity.getEmployeeId(),
                projectNeo4JEntity.getProjectId());

        //then
        assertThat(answer).isFalse();
    }
}
