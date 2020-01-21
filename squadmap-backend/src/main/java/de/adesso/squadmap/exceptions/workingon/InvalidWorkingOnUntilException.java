package de.adesso.squadmap.exceptions.workingon;

public class InvalidWorkingOnUntilException extends  RuntimeException {
    public InvalidWorkingOnUntilException() {
        super("The end date of this relation should not be null");
    }
}
