package de.adesso.squadmap.exceptions.workingon;

public class WorkingOnNotFoundException extends RuntimeException {
    public WorkingOnNotFoundException() {
        super("No matching workingOn relation was found");
    }
}
