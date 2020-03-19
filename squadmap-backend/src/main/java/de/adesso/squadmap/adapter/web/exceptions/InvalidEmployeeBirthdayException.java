package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeBirthdayException extends RuntimeException {
    public InvalidEmployeeBirthdayException() {
        super("The Birthday should not be null and has to be in the past");
    }
}
