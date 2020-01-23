package de.adesso.squadmap.adapter.persistence.exceptions;

public class ProjectAlreadyExistsException extends RuntimeException {
    public ProjectAlreadyExistsException() {
        super("The title is already used");
    }
}
