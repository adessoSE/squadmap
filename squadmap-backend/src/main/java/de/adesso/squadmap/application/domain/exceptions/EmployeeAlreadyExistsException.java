package de.adesso.squadmap.application.domain.exceptions;

public class EmployeeAlreadyExistsException extends AlreadyExistsException {

    public EmployeeAlreadyExistsException(String email) {
        super(String.format("The email %s is already used", email));
    }
}
