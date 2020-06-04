package de.adesso.squadmap.domain.exceptions;

public abstract class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
