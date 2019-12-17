package de.adesso.squadmap.exceptions.project;

public class ProjectAlreadyExistsException extends RuntimeException {
    public ProjectAlreadyExistsException() {
        super("The title of the given project is already used by another project");
    }
}
