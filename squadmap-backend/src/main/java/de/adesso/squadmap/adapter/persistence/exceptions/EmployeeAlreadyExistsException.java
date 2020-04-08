package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;

public class EmployeeAlreadyExistsException extends AlreadyExistsException {
    public EmployeeAlreadyExistsException(String email) {
        super(String.format("The email %s is already used", email));
    }
}
