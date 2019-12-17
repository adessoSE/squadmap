package de.adesso.squadmap.exceptions.project;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super("No matching project was found");
    }
}
