package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeLastNameException extends RuntimeException {
    public InvalidEmployeeLastNameException() {
        super("The last name has to be between 1 and 50 characters long");
    }
}
