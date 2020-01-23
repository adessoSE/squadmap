package de.adesso.squadmap.adapter.persistence.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException() {
        super("The email is already used");
    }
}
