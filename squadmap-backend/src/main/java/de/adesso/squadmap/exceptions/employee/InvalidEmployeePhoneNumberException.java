package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeePhoneNumberException extends RuntimeException {
    public InvalidEmployeePhoneNumberException() {
        super("The phone number should not be empty and has to be valid");
    }
}
