package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;

public class ProjectAlreadyExistsException extends AlreadyExistsException {
    public ProjectAlreadyExistsException() {
        super("The title is already used");
    }
}
