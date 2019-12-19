package de.adesso.squadmap.exceptions.project;

public class InvalidProjectTitleException extends RuntimeException {
    public InvalidProjectTitleException() {
        super("The title should be between 1 and 100 characters long");
    }
}
