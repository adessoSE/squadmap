package de.adesso.squadmap.adapter.persistence.exceptions;

public class WorkingOnNotFoundException extends RuntimeException {
    public WorkingOnNotFoundException() {
        super("No matching workingOn relation was found");
    }
}
