package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {
    public ProjectNotFoundException(long id) {
        super(String.format("No project with id %d was found", id));
    }
}
