package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeePhoneNumberException extends RuntimeException {
    public InvalidEmployeePhoneNumberException() {
        super("The phone number should be empty or has to be valid");
    }
}
