package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeeFirstNameException extends RuntimeException {
    public InvalidEmployeeFirstNameException() {
        super("The first name has to be between 1 and 50 characters long");
    }
}
