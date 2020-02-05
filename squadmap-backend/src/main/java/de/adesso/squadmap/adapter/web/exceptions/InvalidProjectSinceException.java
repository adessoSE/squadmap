package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidProjectSinceException extends RuntimeException {
    public InvalidProjectSinceException() {
        super("The date when the Project started should not be null");
    }
}
