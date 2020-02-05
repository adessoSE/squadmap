package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeIsExternalException extends RuntimeException {
    public InvalidEmployeeIsExternalException() {
        super("IsExternal should not be null");
    }
}
