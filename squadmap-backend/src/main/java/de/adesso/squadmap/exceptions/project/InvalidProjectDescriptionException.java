package de.adesso.squadmap.exceptions.project;

public class InvalidProjectDescriptionException extends RuntimeException {
    public InvalidProjectDescriptionException() {
        super("The description should not be null and not longer than 1000 characters");
    }
}
