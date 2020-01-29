package de.adesso.squadmap.configuration;

import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.time.LocalDate.now;

@Profile("!test")
@Component
@RequiredArgsConstructor
class TestDataGenerator implements CommandLineRunner {

    private final ListEmployeeUseCase listEmployeeUseCase;
    private final ListProjectUseCase listProjectUseCase;
    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final CreateWorkingOnUseCase createWorkingOnUseCase;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void run(String... args) {
        if (!listEmployeeUseCase.listEmployees().isEmpty() || !listProjectUseCase.listProjects().isEmpty()) {
            return;
        }
        String phone = "0162123123";

        CreateEmployeeCommand createEmployeeCommand1 = CreateEmployeeCommand.builder()
                .firstName("Bob").lastName("Apple").birthday(now())
                .email("b.a@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand2 = CreateEmployeeCommand.builder()
                .firstName("Kevin").lastName("Peach").birthday(now())
                .email("k.p@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand3 = CreateEmployeeCommand.builder()
                .firstName("Bert").lastName("Melon").birthday(now())
                .email("b.m@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand4 = CreateEmployeeCommand.builder()
                .firstName("Treena").lastName("Utecht").birthday(now())
                .email("t.u@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand5 = CreateEmployeeCommand.builder()
                .firstName("Chris").lastName("Urbaniak").birthday(now())
                .email("c.u@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand6 = CreateEmployeeCommand.builder()
                .firstName("Kiley").lastName("Dula").birthday(now())
                .email("k.d@adesso.de").phone(phone).isExternal(false).image("")
                .build();

        CreateEmployeeCommand createEmployeeCommand7 = CreateEmployeeCommand.builder()
                .firstName("Reba").lastName("Raisor").birthday(now())
                .email("r.r@adesso.de").phone(phone).isExternal(false).image("")
                .build();

        CreateEmployeeCommand createEmployeeCommand8 = CreateEmployeeCommand.builder()
                .firstName("Long").lastName("Staillings").birthday(now())
                .email("l.s@adesso.de").phone(phone).isExternal(false).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand9 = CreateEmployeeCommand.builder()
                .firstName("Herma").lastName("Tworek").birthday(now())
                .email("h.t@adesso.de").phone(phone).isExternal(true).image("")
                .build();
        CreateEmployeeCommand createEmployeeCommand10 = CreateEmployeeCommand.builder()
                .firstName("Gloria").lastName("Nunley").birthday(now())
                .email("g.n@adesso.de").phone(phone).isExternal(true).image("")
                .build();


        CreateProjectCommand createProjectCommand1 = CreateProjectCommand.builder()
                .title("squadmap").description("Description of squadmap")
                .since(now()).until(now())
                .isExternal(false).sites(new ArrayList<>())
                .build();
        CreateProjectCommand createProjectCommand2 = CreateProjectCommand.builder()
                .title("coderadar").description("Description of coderadar")
                .since(now()).until(now())
                .isExternal(false).sites(new ArrayList<>())
                .build();
        CreateProjectCommand createProjectCommand3 = CreateProjectCommand.builder()
                .title("devblog").description("Description of devblog")
                .since(now()).until(now())
                .isExternal(false).sites(new ArrayList<>())
                .build();
        CreateProjectCommand createProjectCommand4 = CreateProjectCommand.builder()
                .title("project-board").description("Description of project-board")
                .since(now()).until(now())
                .isExternal(true).sites(new ArrayList<>())
                .build();

        long employee1Id = createEmployeeUseCase.createEmployee(createEmployeeCommand1);
        long employee2Id = createEmployeeUseCase.createEmployee(createEmployeeCommand2);
        long employee3Id = createEmployeeUseCase.createEmployee(createEmployeeCommand3);
        long employee4Id = createEmployeeUseCase.createEmployee(createEmployeeCommand4);
        long employee5Id = createEmployeeUseCase.createEmployee(createEmployeeCommand5);
        long employee6Id = createEmployeeUseCase.createEmployee(createEmployeeCommand6);
        long employee7Id = createEmployeeUseCase.createEmployee(createEmployeeCommand7);
        long employee8Id = createEmployeeUseCase.createEmployee(createEmployeeCommand8);
        long employee9Id = createEmployeeUseCase.createEmployee(createEmployeeCommand9);
        createEmployeeUseCase.createEmployee(createEmployeeCommand10);

        long project1Id = createProjectUseCase.createProject(createProjectCommand1);
        long project2Id = createProjectUseCase.createProject(createProjectCommand2);
        long project3Id = createProjectUseCase.createProject(createProjectCommand3);
        long project4Id = createProjectUseCase.createProject(createProjectCommand4);

        CreateWorkingOnCommand createWorkingOnCommand1 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee1Id).projectId(project1Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand2 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee1Id).projectId(project2Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand3 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee2Id).projectId(project2Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand4 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee3Id).projectId(project4Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand5 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee4Id).projectId(project4Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand6 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee5Id).projectId(project4Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand7 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee6Id).projectId(project2Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand8 = CreateWorkingOnCommand.builder()
                .since(now()).until(now().plusMonths(5)).workload(0)
                .employeeId(employee7Id).projectId(project1Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand9 = CreateWorkingOnCommand.builder()
                .since(now()).until(now()).workload(0)
                .employeeId(employee8Id).projectId(project2Id)
                .build();
        CreateWorkingOnCommand createWorkingOnCommand10 = CreateWorkingOnCommand.builder()
                .since(now()).until(now()).workload(0)
                .employeeId(employee9Id).projectId(project3Id)
                .build();

        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand1);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand2);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand3);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand4);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand5);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand6);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand7);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand8);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand9);
        createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand10);

    }
}
