package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeeLastNameException extends RuntimeException {
    public InvalidEmployeeLastNameException() {
        super("The last name has to be between 1 and 50 characters long");
    }
}
