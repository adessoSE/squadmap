package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidEmployeeImageException extends RuntimeException{
    public InvalidEmployeeImageException() { super("The Image should be empty or a valid url"); }
}
