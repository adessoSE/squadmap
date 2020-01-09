package de.adesso.squadmap.exceptions.employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("No matching employee was found");
    }
}
