package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;

public class ProjectAlreadyExistsException extends AlreadyExistsException {
    public ProjectAlreadyExistsException(String title) {
        super(String.format("The title %s is already used", title));
    }
}
