package de.adesso.squadmap.domain.exceptions;

public class EmployeeNotFoundException extends NotFoundException {

    public EmployeeNotFoundException(long id) {
        super(String.format("No de.adesso.squadmap.driver.employee with id %d was found", id));
    }
}
