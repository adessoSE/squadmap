package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidWorkingOnUntilException extends  RuntimeException {
    public InvalidWorkingOnUntilException() {
        super("The end date of this relation should not be null");
    }
}
