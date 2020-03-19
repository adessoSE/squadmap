package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeImageException extends RuntimeException {
    public InvalidEmployeeImageException() {
        super("The Image should not be null");
    }
}
