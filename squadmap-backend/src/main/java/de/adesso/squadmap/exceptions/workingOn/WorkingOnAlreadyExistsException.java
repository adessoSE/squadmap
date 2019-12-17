package de.adesso.squadmap.exceptions.workingOn;

public class WorkingOnAlreadyExistsException extends RuntimeException {
    public WorkingOnAlreadyExistsException() {
        super("The workingOn relation between given employee and project already exists");
    }
}
