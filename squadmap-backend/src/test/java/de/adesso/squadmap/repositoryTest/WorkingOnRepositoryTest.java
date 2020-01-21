package de.adesso.squadmap.repositoryTest;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

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
        Employee employee = new Employee();
        Project project = new Project();
        WorkingOn workingOn = new WorkingOn(employee, project, LocalDate.now(), LocalDate.now(), 0);
        projectRepository.save(project);
        employeeRepository.save(employee);
        workingOnRepository.save(workingOn);

        //when
        boolean answer = workingOnRepository.existsByEmployeeAndProject(employee.getEmployeeId(), project.getProjectId());

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void checkIfExistsByEmployeeAndProjectReturnsFalseWhenFalse() {
        //when
        Employee employee = new Employee();
        Project project = new Project();
        employee.setEmployeeId(1L);
        project.setProjectId(1L);
        boolean answer = workingOnRepository.existsByEmployeeAndProject(employee.getEmployeeId(), project.getProjectId());

        //then
        assertThat(answer).isFalse();
    }
}
