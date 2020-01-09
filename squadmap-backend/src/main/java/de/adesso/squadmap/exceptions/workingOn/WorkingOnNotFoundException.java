package de.adesso.squadmap.exceptions.workingOn;

public class WorkingOnNotFoundException extends RuntimeException {
    public WorkingOnNotFoundException() {
        super("No matching workingOn relation was found");
    }
}
