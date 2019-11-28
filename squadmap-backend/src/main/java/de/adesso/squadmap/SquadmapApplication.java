package de.adesso.squadmap;

import de.adesso.squadmap.models.Employee;
import de.adesso.squadmap.models.Project;
import de.adesso.squadmap.models.WorkingOn;
import de.adesso.squadmap.repository.EmployeeRepository;
import de.adesso.squadmap.repository.ProjectRepository;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@EnableNeo4jRepositories
public class SquadmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquadmapApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner demo(EmployeeRepository employeeRepository, ProjectRepository projectRepository, WorkingOnRepository workingOnRepository) {
        return args -> {
            employeeRepository.deleteAll();
            projectRepository.deleteAll();
            workingOnRepository.deleteAll();

            Employee employee1 = new Employee("Bob", "Apple", LocalDate.now(), "b.a@something.com", "001", false);
            Employee employee2 = new Employee("Dave", "Beer", LocalDate.now(), "d.b@something.com", "002", false);
            Employee employee3 = new Employee("Gert", "Cucumber", LocalDate.now(), "g.c@something.com", "003", true);

            Project project1 = new Project("Squadmap", "some Project", LocalDate.now(), LocalDate.now(), false);
            Project project2 = new Project("Coderadar", "some Project", LocalDate.now(), LocalDate.now(), false);
            Project project3 = new Project("Budgeteer", "some Project", LocalDate.now(), LocalDate.now(), true);

            WorkingOn workingOn1 = new WorkingOn(employee1, project1, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn2 = new WorkingOn(employee1, project2, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn3 = new WorkingOn(employee2, project2, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn4 = new WorkingOn(employee3, project3, LocalDate.now(), LocalDate.now());

            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));
            projectRepository.saveAll(Arrays.asList(project1, project2, project3));
            workingOnRepository.saveAll(Arrays.asList(workingOn1, workingOn2, workingOn3, workingOn4));
        };
    }
}
