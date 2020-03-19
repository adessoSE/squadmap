package de.adesso.squadmap.adapter.web.exceptions;

public class InvalidProjectUrlListException extends RuntimeException {
    public InvalidProjectUrlListException() {
        super("The list should only contain valid urls");
    }
}
