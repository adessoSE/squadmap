package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeeEmailException extends  RuntimeException {
    public InvalidEmployeeEmailException() {
        super("The email should not be empty and has to be valid");
    }
}
