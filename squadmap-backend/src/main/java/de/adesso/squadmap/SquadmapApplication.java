package de.adesso.squadmap;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@EnableNeo4jRepositories("de.adesso.squadmap.repository")
public class SquadmapApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SquadmapApplication.class, args);
        try {
            openHomePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void openHomePage() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:4200/");
    }

    @Bean
    @Profile("!test")
    CommandLineRunner demo(EmployeeRepository employeeRepository, ProjectRepository projectRepository, WorkingOnRepository workingOnRepository) {
        return args -> {
            employeeRepository.deleteAll();
            projectRepository.deleteAll();
            workingOnRepository.deleteAll();

            Employee employee1 = new Employee("Bob", "Apple", LocalDate.now(), "b.a@adesso.de", "0162123123", false);
            Employee employee2 = new Employee("Kevin", "Peach", LocalDate.now(), "k.p@adesso.de", "0162123123", false);
            Employee employee3 = new Employee("Bert", "Melon", LocalDate.now(), "b.m@adesso.de", "0162123123", false);
            Employee employee4 = new Employee("Treena", "Utecht", LocalDate.now(), "t.u@adesso.de", "0162123123", false);
            Employee employee5 = new Employee("Chris", "Urbaniak", LocalDate.now(), "c.u@adesso.de", "0162123123", false);
            Employee employee6 = new Employee("Kiley", "Dula", LocalDate.now(), "k.d@adesso.de", "0162123123", false);
            Employee employee7 = new Employee("Reba", "Raisor", LocalDate.now(), "r.r@adesso.de", "0162123123", false);
            Employee employee8 = new Employee("Long", "Staillings", LocalDate.now(), "l.s@adesso.de", "0162123123", false);
            Employee employee9 = new Employee("Herma", "Tworek", LocalDate.now(), "h.t@adesso.de", "0162123123", true);
            Employee employee10 = new Employee("Gloria", "Nunley", LocalDate.now(), "g.n@adesso.de", "0162123123", true);

            Project project1 = new Project("squadmap", "Description of squadmap", LocalDate.now(), LocalDate.of(2015,12,15), false);
            Project project2 = new Project("coderadar", "Description of coderadar", LocalDate.now(), LocalDate.now(), false);
            Project project3 = new Project("devblog", "Desscription of squadmap", LocalDate.now(), LocalDate.now(), false);
            Project project4 = new Project("project-board", "Desscription of project-board", LocalDate.now(), LocalDate.now(), true);

            WorkingOn workingOn1 = new WorkingOn(employee1, project1, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn2 = new WorkingOn(employee1, project2, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn3 = new WorkingOn(employee2, project2, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn4 = new WorkingOn(employee3, project4, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn5 = new WorkingOn(employee4, project4, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn6 = new WorkingOn(employee5, project4, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn7 = new WorkingOn(employee6, project2, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn8 = new WorkingOn(employee7, project1, LocalDate.now(), LocalDate.now().plusMonths(5));
            WorkingOn workingOn9 = new WorkingOn(employee8, project2, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn10 = new WorkingOn(employee9, project3, LocalDate.now(), LocalDate.now());

            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3, employee4,employee5,employee6,employee7,employee8,employee9,employee10));
            projectRepository.saveAll(Arrays.asList(project1, project2, project3, project4));
            workingOnRepository.saveAll(Arrays.asList(workingOn1, workingOn2, workingOn3, workingOn4, workingOn5, workingOn6, workingOn7, workingOn8, workingOn9, workingOn10));
        };
    }
}
