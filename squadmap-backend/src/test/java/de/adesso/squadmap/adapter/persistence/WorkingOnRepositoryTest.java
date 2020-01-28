package de.adesso.squadmap.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().employeeId(null).build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().projectId(null).build();
        employeeNeo4JEntity = employeeRepository.save(employeeNeo4JEntity);
        projectNeo4JEntity = projectRepository.save(projectNeo4JEntity);
        WorkingOnNeo4JEntity workingOnNeo4JEntity = WorkingOnNeo4JEntityMother.complete()
                .workingOnId(null)
                .employee(employeeNeo4JEntity)
                .project(projectNeo4JEntity)
                .build();
        workingOnRepository.save(workingOnNeo4JEntity);

        //when
        boolean answer = workingOnRepository.existsByEmployeeAndProject(
                workingOnNeo4JEntity.getEmployee().getEmployeeId(),
                workingOnNeo4JEntity.getProject().getProjectId());

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void checkIfExistsByEmployeeAndProjectReturnsFalseWhenFalse() {
        //given
        EmployeeNeo4JEntity employeeNeo4JEntity = EmployeeNeo4JEntityMother.complete().build();
        ProjectNeo4JEntity projectNeo4JEntity = ProjectNeo4JEntityMother.complete().build();

        //when
        boolean answer = workingOnRepository.existsByEmployeeAndProject(
                employeeNeo4JEntity.getEmployeeId(),
                projectNeo4JEntity.getProjectId());

        //then
        assertThat(answer).isFalse();
    }
}
