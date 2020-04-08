package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.NotFoundException;

public class WorkingOnNotFoundException extends NotFoundException {
    public WorkingOnNotFoundException(long id) {
        super(String.format("No workingOn relation with id %d was found", id));
    }
}
