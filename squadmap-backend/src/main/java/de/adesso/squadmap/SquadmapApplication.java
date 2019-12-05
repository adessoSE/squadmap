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

            Employee employee1 = new Employee("Bob", "Apple", LocalDate.now(), "b.a@adesso.de", "0", false);
            Employee employee2 = new Employee("Kevin", "Peach", LocalDate.now(), "k.p@adesso.de", "1", false);
            Employee employee3 = new Employee("Bert", "Melon", LocalDate.now(), "b.m@adesso.de", "2", true);

            Project project1 = new Project("squadmap", "something", LocalDate.now(), LocalDate.now(), false);
            Project project2 = new Project("coderadar", "other", LocalDate.now(), LocalDate.now(), false);
            Project project3 = new Project("devblog", "another", LocalDate.now(), LocalDate.now(), true);

            WorkingOn workingOn1 = new WorkingOn(employee1, project1, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn2 = new WorkingOn(employee1, project2, LocalDate.now(), LocalDate.now());
            WorkingOn workingOn3 = new WorkingOn(employee2, project2, LocalDate.now(), LocalDate.now());

            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));
            projectRepository.saveAll(Arrays.asList(project1, project2, project3));
            workingOnRepository.saveAll(Arrays.asList(workingOn1, workingOn2, workingOn3));
        };
    }
}
