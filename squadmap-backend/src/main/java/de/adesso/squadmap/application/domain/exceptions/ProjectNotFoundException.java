package de.adesso.squadmap.application.domain.exceptions;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(long id) {
        super(String.format("No project with id %d was found", id));
    }
}
