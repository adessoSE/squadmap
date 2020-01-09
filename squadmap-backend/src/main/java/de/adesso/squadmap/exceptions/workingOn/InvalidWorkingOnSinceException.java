package de.adesso.squadmap.exceptions.workingOn;

public class InvalidWorkingOnSinceException extends RuntimeException {
    public InvalidWorkingOnSinceException() {
        super("The starting date of this relation should not be null");
    }
}
