package de.adesso.squadmap.exceptions.project;

public class InvalidProjectSinceException extends RuntimeException {
    public InvalidProjectSinceException() {
        super("The date when the Project started should not be null");
    }
}
