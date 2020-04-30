package de.adesso.squadmap.application.domain.exceptions;

public class EmployeeNotFoundException extends NotFoundException {

    public EmployeeNotFoundException(long id) {
        super(String.format("No employee with id %d was found", id));
    }
}
