package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {
    public ProjectNotFoundException() {
        super("No matching project was found");
    }
}
