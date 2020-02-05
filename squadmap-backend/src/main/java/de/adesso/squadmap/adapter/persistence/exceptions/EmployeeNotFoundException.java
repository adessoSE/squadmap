package de.adesso.squadmap.adapter.persistence.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("No matching employee was found");
    }
}
