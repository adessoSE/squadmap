package de.adesso.squadmap.adapter.persistence.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super("No matching project was found");
    }
}
