package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidProjectUntilException extends RuntimeException {
    public InvalidProjectUntilException() {
        super("The date when the project ends should not be null");
    }
}
