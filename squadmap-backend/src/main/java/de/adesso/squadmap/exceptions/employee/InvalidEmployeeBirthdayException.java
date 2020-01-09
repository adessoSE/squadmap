package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeeBirthdayException extends RuntimeException {
    public InvalidEmployeeBirthdayException(){
        super("The Birthday should not be null and has to be in the past");
    }
}
