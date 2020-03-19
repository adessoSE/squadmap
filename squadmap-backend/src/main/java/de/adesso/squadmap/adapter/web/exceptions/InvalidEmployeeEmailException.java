package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeEmailException extends RuntimeException {
    public InvalidEmployeeEmailException() {
        super("The email should not be empty and has to be valid");
    }
}
