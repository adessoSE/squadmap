package de.adesso.squadmap.exceptions.workingon;

public class InvalidWorkingOnWorkloadException extends RuntimeException {
    public InvalidWorkingOnWorkloadException() { super("The workload has to be between 0 and 100"); }
}
