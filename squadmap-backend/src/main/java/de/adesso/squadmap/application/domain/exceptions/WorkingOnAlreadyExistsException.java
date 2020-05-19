package de.adesso.squadmap.application.domain.exceptions;

public class WorkingOnAlreadyExistsException extends AlreadyExistsException {

    public WorkingOnAlreadyExistsException(long employee, long project) {
        super(String.format("The workingOn relation between employee %d and project %d already exists", employee, project));
    }
}
