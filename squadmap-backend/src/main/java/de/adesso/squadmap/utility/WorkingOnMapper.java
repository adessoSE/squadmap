package de.adesso.squadmap.utility;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;

public class WorkingOnMapper {

    public static GetWorkingOnResponse mapWorkingOnToWorkingOnResponse(WorkingOn workingOn) {
        GetEmployeeResponse employee = EmployeeMapper.mapEmployeeToEmployeeResponse(workingOn.getEmployee());
        GetProjectResponse project = ProjectMapper.mapProjectToGetProjectResponse(workingOn.getProject());
        GetWorkingOnResponse response = new GetWorkingOnResponse(
                workingOn.getWorkingOnId(),
                employee,
                project,
                workingOn.getSince(),
                workingOn.getUntil());
        return response;
    }

    public static WorkingOn mapCreateWorkingOnCommandToWorkingOn(CreateWorkingOnCommand command){
        return new WorkingOn(
                getEmployee(command.getEmployee()),
                getProject(command.getProject()),
                command.getSince(),
                command.getUntil());
    }

    public static WorkingOn mapUpdateWorkingOnCommandToWorkingOn(UpdateWorkingOnCommand command, long workingOnId){
        WorkingOn workingOn = new WorkingOn(
                getEmployee(command.getEmployee()),
                getProject(command.getProject()),
                command.getSince(),
                command.getUntil());
        workingOn.setWorkingOnId(workingOnId);
        return workingOn;
    }

    private static Employee getEmployee(GetEmployeeResponse getEmployeeResponse){
        Employee employee = new Employee(
                getEmployeeResponse.getFirstName(),
                getEmployeeResponse.getLastName(),
                getEmployeeResponse.getBirthday(),
                getEmployeeResponse.getEmail(),
                getEmployeeResponse.getPhone(),
                getEmployeeResponse.getIsExternal());
        employee.setEmployeeId(getEmployeeResponse.getEmployeeId());
        return employee;
    }

    private static Project getProject(GetProjectResponse getProjectResponse){
        Project project = new Project(
                getProjectResponse.getTitle(),
                getProjectResponse.getDescription(),
                getProjectResponse.getSince(),
                getProjectResponse.getUntil(),
                getProjectResponse.getIsExternal());
        project.setProjectId(getProjectResponse.getProjectId());
        return project;
    }
}
