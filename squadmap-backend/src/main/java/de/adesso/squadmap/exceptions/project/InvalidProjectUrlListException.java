package de.adesso.squadmap.exceptions.project;

public class InvalidProjectUrlListException extends RuntimeException{
    public InvalidProjectUrlListException(){ super("The list should only contain valid urls"); }
}
