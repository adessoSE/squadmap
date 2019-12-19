package de.adesso.squadmap.exceptions.project;

public class InvalidProjectUntilException extends RuntimeException {
    public InvalidProjectUntilException() {
        super("The date when the project ends should not be null");
    }
}
