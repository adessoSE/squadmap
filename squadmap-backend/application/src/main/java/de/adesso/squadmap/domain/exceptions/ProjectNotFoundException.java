package de.adesso.squadmap.domain.exceptions;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(long id) {
        super(String.format("No de.adesso.squadmap.driver.project with id %d was found", id));
    }
}
