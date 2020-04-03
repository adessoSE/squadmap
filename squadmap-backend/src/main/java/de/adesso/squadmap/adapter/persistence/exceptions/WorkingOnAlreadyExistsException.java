package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;

public class WorkingOnAlreadyExistsException extends AlreadyExistsException {
    public WorkingOnAlreadyExistsException() {
        super("The workingOn relation between given employee and project already exists");
    }
}
