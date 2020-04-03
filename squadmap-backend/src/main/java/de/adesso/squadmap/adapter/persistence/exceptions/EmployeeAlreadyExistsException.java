package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;

public class EmployeeAlreadyExistsException extends AlreadyExistsException {
    public EmployeeAlreadyExistsException() {
        super("The email is already used");
    }
}
