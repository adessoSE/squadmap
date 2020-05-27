package de.adesso.squadmap.domain.exceptions;

public class WorkingOnNotFoundException extends NotFoundException {

    public WorkingOnNotFoundException(long id) {
        super(String.format("No workingOn relation with id %d was found", id));
    }
}
