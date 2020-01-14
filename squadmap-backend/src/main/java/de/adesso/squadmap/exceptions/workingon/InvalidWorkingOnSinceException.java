package de.adesso.squadmap.exceptions.workingon;

public class InvalidWorkingOnSinceException extends RuntimeException {
    public InvalidWorkingOnSinceException() {
        super("The starting date of this relation should not be null");
    }
}
