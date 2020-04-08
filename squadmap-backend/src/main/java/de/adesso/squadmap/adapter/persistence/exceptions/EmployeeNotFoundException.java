package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.NotFoundException;

public class EmployeeNotFoundException extends NotFoundException {
    public EmployeeNotFoundException(long id) {
        super(String.format("No employee with id %d was found", id));
    }
}
