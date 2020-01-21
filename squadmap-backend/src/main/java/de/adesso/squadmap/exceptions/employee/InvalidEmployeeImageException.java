package de.adesso.squadmap.exceptions.employee;

public class InvalidEmployeeImageException extends RuntimeException{
    public InvalidEmployeeImageException() { super("The Image should be empty or a valid url"); }
}
