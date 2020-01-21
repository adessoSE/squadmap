package de.adesso.squadmap.configuration;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.LocalDate.now;

@Profile("!test")
@Component
public class TestDataGenerator implements CommandLineRunner {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private WorkingOnRepository workingOnRepository;

    public TestDataGenerator(EmployeeRepository employeeRepository, ProjectRepository projectRepository, WorkingOnRepository workingOnRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.workingOnRepository = workingOnRepository;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void run(String... args) {
        if (employeeRepository.count() != 0 || projectRepository.count() != 0 || workingOnRepository.count() != 0) {
            return;
        }
        String phone = "0162123123";

        Employee employee1 = new Employee("Bob", "Apple", now(), "b.a@adesso.de", phone, false, "");
        Employee employee2 = new Employee("Kevin", "Peach", now(), "k.p@adesso.de", phone, false, "");
        Employee employee3 = new Employee("Bert", "Melon", now(), "b.m@adesso.de", phone, false, "");
        Employee employee4 = new Employee("Treena", "Utecht", now(), "t.u@adesso.de", phone, false, "");
        Employee employee5 = new Employee("Chris", "Urbaniak", now(), "c.u@adesso.de", phone, false, "");
        Employee employee6 = new Employee("Kiley", "Dula", now(), "k.d@adesso.de", phone, false, "");
        Employee employee7 = new Employee("Reba", "Raisor", now(), "r.r@adesso.de", phone, false, "");
        Employee employee8 = new Employee("Long", "Staillings", now(), "l.s@adesso.de", phone, false, "");
        Employee employee9 = new Employee("Herma", "Tworek", now(), "h.t@adesso.de", phone, true, "");
        Employee employee10 = new Employee("Gloria", "Nunley", now(), "g.n@adesso.de", phone, true, "");

        Project project1 = new Project("squadmap", "Description of squadmap", now(), LocalDate.of(2015, 12, 15), false, new ArrayList<>());
        Project project2 = new Project("coderadar", "Description of coderadar", now(), now(), false, new ArrayList<>());
        Project project3 = new Project("devblog", "Description of squadmap", now(), now(), false, new ArrayList<>());
        Project project4 = new Project("project-board", "Description of project-board", now(), now(), true, new ArrayList<>());

        WorkingOn workingOn1 = new WorkingOn(employee1, project1, now(), now().plusMonths(5), 0);
        WorkingOn workingOn2 = new WorkingOn(employee1, project2, now(), now().plusMonths(5), 0);
        WorkingOn workingOn3 = new WorkingOn(employee2, project2, now(), now().plusMonths(5), 0);
        WorkingOn workingOn4 = new WorkingOn(employee3, project4, now(), now().plusMonths(5), 0);
        WorkingOn workingOn5 = new WorkingOn(employee4, project4, now(), now().plusMonths(5), 0);
        WorkingOn workingOn6 = new WorkingOn(employee5, project4, now(), now().plusMonths(5), 0);
        WorkingOn workingOn7 = new WorkingOn(employee6, project2, now(), now().plusMonths(5), 0);
        WorkingOn workingOn8 = new WorkingOn(employee7, project1, now(), now().plusMonths(5), 0);
        WorkingOn workingOn9 = new WorkingOn(employee8, project2, now(), now(), 0);
        WorkingOn workingOn10 = new WorkingOn(employee9, project3, now(), now(), 0);

        employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6, employee7, employee8, employee9, employee10));
        projectRepository.saveAll(Arrays.asList(project1, project2, project3, project4));
        workingOnRepository.saveAll(Arrays.asList(workingOn1, workingOn2, workingOn3, workingOn4, workingOn5, workingOn6, workingOn7, workingOn8, workingOn9, workingOn10));
    }
}
