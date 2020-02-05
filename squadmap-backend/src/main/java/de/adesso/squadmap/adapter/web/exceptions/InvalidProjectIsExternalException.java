package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidProjectIsExternalException extends RuntimeException {
    public InvalidProjectIsExternalException() {
        super("IsExternal should not be null");
    }
}
