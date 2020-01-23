package de.adesso.squadmap.configuration;

import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.Project;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driven.employee.CreateEmployeePort;
import de.adesso.squadmap.application.port.driven.employee.ListEmployeePort;
import de.adesso.squadmap.application.port.driven.project.CreateProjectPort;
import de.adesso.squadmap.application.port.driven.project.ListProjectPort;
import de.adesso.squadmap.application.port.driven.workingon.CreateWorkingOnPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.LocalDate.now;

@Profile("!test")
@Component
@RequiredArgsConstructor
class TestDataGenerator implements CommandLineRunner {

    private final ListEmployeePort listEmployeePort;
    private final ListProjectPort listProjectPort;
    private final CreateEmployeePort createEmployeePort;
    private final CreateProjectPort createProjectPort;
    private final CreateWorkingOnPort createWorkingOnPort;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void run(String... args) {
        if (listEmployeePort.listEmployees().size() == 0 && listProjectPort.listProjects().size() == 0) {
            String phone = "0162123123";

            Employee employee1 = Employee.withoutId("Bob", "Apple", now(), "b.a@adesso.de", phone, false, "");
            Employee employee2 = Employee.withoutId("Kevin", "Peach", now(), "k.p@adesso.de", phone, false, "");
            Employee employee3 = Employee.withoutId("Bert", "Melon", now(), "b.m@adesso.de", phone, false, "");
            Employee employee4 = Employee.withoutId("Treena", "Utecht", now(), "t.u@adesso.de", phone, false, "");
            Employee employee5 = Employee.withoutId("Chris", "Urbaniak", now(), "c.u@adesso.de", phone, false, "");
            Employee employee6 = Employee.withoutId("Kiley", "Dula", now(), "k.d@adesso.de", phone, false, "");
            Employee employee7 = Employee.withoutId("Reba", "Raisor", now(), "r.r@adesso.de", phone, false, "");
            Employee employee8 = Employee.withoutId("Long", "Staillings", now(), "l.s@adesso.de", phone, false, "");
            Employee employee9 = Employee.withoutId("Herma", "Tworek", now(), "h.t@adesso.de", phone, true, "");
            Employee employee10 = Employee.withoutId("Gloria", "Nunley", now(), "g.n@adesso.de", phone, true, "");

            Project project1 = Project.withoutId("squadmap", "Description of squadmap", now(), LocalDate.of(2015, 12, 15), false, new ArrayList<>());
            Project project2 = Project.withoutId("coderadar", "Description of coderadar", now(), now(), false, new ArrayList<>());
            Project project3 = Project.withoutId("devblog", "Description of squadmap", now(), now(), false, new ArrayList<>());
            Project project4 = Project.withoutId("project-board", "Description of project-board", now(), now(), true, new ArrayList<>());

            WorkingOn workingOn1 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee1, project1);
            WorkingOn workingOn2 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee1, project2);
            WorkingOn workingOn3 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee2, project2);
            WorkingOn workingOn4 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee3, project4);
            WorkingOn workingOn5 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee4, project4);
            WorkingOn workingOn6 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee5, project4);
            WorkingOn workingOn7 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee6, project2);
            WorkingOn workingOn8 = WorkingOn.withoutId(now(), now().plusMonths(5), 0, employee7, project1);
            WorkingOn workingOn9 = WorkingOn.withoutId(now(), now(), 0, employee8, project2);
            WorkingOn workingOn10 = WorkingOn.withoutId(now(), now(), 0, employee9, project3);

            createEmployeePort.createEmployee(employee1);
            createEmployeePort.createEmployee(employee2);
            createEmployeePort.createEmployee(employee3);
            createEmployeePort.createEmployee(employee4);
            createEmployeePort.createEmployee(employee5);
            createEmployeePort.createEmployee(employee6);
            createEmployeePort.createEmployee(employee7);
            createEmployeePort.createEmployee(employee8);
            createEmployeePort.createEmployee(employee9);
            createEmployeePort.createEmployee(employee10);

            createProjectPort.createProject(project1);
            createProjectPort.createProject(project2);
            createProjectPort.createProject(project3);
            createProjectPort.createProject(project4);

            createWorkingOnPort.createWorkingOn(workingOn1);
            createWorkingOnPort.createWorkingOn(workingOn2);
            createWorkingOnPort.createWorkingOn(workingOn3);
            createWorkingOnPort.createWorkingOn(workingOn4);
            createWorkingOnPort.createWorkingOn(workingOn5);
            createWorkingOnPort.createWorkingOn(workingOn6);
            createWorkingOnPort.createWorkingOn(workingOn7);
            createWorkingOnPort.createWorkingOn(workingOn8);
            createWorkingOnPort.createWorkingOn(workingOn9);
            createWorkingOnPort.createWorkingOn(workingOn10);
        }
    }
}
