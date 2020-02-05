package de.adesso.squadmap.adapter.persistence.exceptions;

public class WorkingOnAlreadyExistsException extends RuntimeException {
    public WorkingOnAlreadyExistsException() {
        super("The workingOn relation between given employee and project already exists");
    }
}
