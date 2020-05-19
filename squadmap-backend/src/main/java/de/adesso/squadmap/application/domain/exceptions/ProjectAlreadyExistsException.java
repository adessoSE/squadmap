package de.adesso.squadmap.application.domain.exceptions;

public class ProjectAlreadyExistsException extends AlreadyExistsException {

    public ProjectAlreadyExistsException(String title) {
        super(String.format("The title %s is already used", title));
    }
}
