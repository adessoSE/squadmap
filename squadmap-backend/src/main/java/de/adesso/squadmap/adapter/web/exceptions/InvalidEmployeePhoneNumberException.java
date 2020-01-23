package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeePhoneNumberException extends RuntimeException {
    public InvalidEmployeePhoneNumberException() {
        super("The phone number should not be empty and has to be valid");
    }
}
